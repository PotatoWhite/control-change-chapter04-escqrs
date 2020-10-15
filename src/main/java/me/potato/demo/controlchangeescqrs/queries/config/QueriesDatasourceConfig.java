package me.potato.demo.controlchangeescqrs.queries.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "me.potato.demo.controlchangeescqrs.queries.stores", entityManagerFactoryRef = "queriesEntityManager", transactionManagerRef = "queriesTransactionManager")
public class QueriesDatasourceConfig {
  private final Environment env;

  public QueriesDatasourceConfig(Environment env) {
    this.env = env;
  }


  @Bean
  public DataSource queriesDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("spring.datasource.queries.driver-class-name"));
    dataSource.setUrl(env.getProperty("spring.datasource.queries.url"));
    dataSource.setUsername(env.getProperty("spring.datasource.queries.username"));
    dataSource.setPassword(env.getProperty("spring.datasource.queries.password"));

    return dataSource;
  }

  @Bean(name = "queriesEntityManager")
  public LocalContainerEntityManagerFactoryBean queriesEntityManagerFactory(EntityManagerFactoryBuilder builder) {
    return builder.dataSource(queriesDataSource()).properties(hibernateProperties()).packages("me.potato.demo.controlchangeescqrs.queries.entities").persistenceUnit("commands").build();
  }

  @Bean(name = "queriesTransactionManager")
  PlatformTransactionManager queriesTransactionManager(@Qualifier("queriesEntityManager") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  private Map hibernateProperties() {
    Resource resource = new ClassPathResource("hibernate.properties");

    try {
      Properties properties = PropertiesLoaderUtils.loadProperties(resource);

      return properties.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue()));
    } catch (IOException e) {
      return new HashMap();
    }
  }

}
