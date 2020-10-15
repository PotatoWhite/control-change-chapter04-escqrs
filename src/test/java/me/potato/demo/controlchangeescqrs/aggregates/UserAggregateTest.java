package me.potato.demo.controlchangeescqrs.aggregates;


import me.potato.demo.controlchangeescqrs.commands.UserAggregate;
import me.potato.demo.controlchangeescqrs.commands.commands.AddAddressesCommand;
import me.potato.demo.controlchangeescqrs.commands.commands.AddContactsCommand;
import me.potato.demo.controlchangeescqrs.commands.commands.CreateUserCommand;
import me.potato.demo.controlchangeescqrs.commands.commands.DeleteUserCommand;
import me.potato.demo.controlchangeescqrs.commands.events.stores.entities.UserEvent;
import me.potato.demo.controlchangeescqrs.queries.UserProjections;
import me.potato.demo.controlchangeescqrs.queries.entities.User;
import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Address;
import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Contact;
import me.potato.demo.controlchangeescqrs.queries.queries.UserByCityQuery;
import me.potato.demo.controlchangeescqrs.queries.queries.UserByDetailQuery;
import me.potato.demo.controlchangeescqrs.queries.queries.UsersByCityQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(properties = "spring.config.location=classpath:/application-test.yml")
class UserAggregateTest {

  @Autowired
  UserAggregate userAggregate;

  @Autowired
  UserProjections userProjections;

  @Test
  public void create() throws Exception {
    // command
    UserEvent created = userAggregate.handle(new CreateUserCommand("test", "홍길동"));
    assertEquals(true, !created.getAggregateId().isEmpty());


    Set<Address> addresses = new LinkedHashSet<>();
    addresses.add(new Address("1234", "seoul"));
    addresses.add(new Address("5678", "busan"));
    // command
    UserEvent address = userAggregate.handle(new AddAddressesCommand(created.getAggregateId(), addresses));
    assertEquals(true, !created.getAggregateId().isEmpty());


    Set<Contact> contacts = new LinkedHashSet<>();
    contacts.add(new Contact("home", "01000000000"));
    contacts.add(new Contact("company", "02000000000"));
    // command
    UserEvent contact = userAggregate.handle(new AddContactsCommand(address.getAggregateId(), contacts));


    User seoul = userProjections.handle(new UserByCityQuery(contact.getAggregateId(), "seoul"));
    assertEquals(true, seoul != null);

    User inchon = userProjections.handle(new UserByCityQuery(contact.getAggregateId(), "inchon"));
    assertEquals(false, inchon != null);

    User c01000000000 = userProjections.handle(new UserByDetailQuery(contact.getAggregateId(), "01000000000"));
    assertEquals(true, c01000000000 != null);

    User c03000000000 = userProjections.handle(new UserByDetailQuery(contact.getAggregateId(), "03000000000"));
    assertEquals(false, c03000000000 != null);

    Set<User> busan = userProjections.handle(new UsersByCityQuery("busan"));
    assertEquals(true, !busan.isEmpty());

    Set<User> newyork = userProjections.handle(new UsersByCityQuery("newyork"));
    assertEquals(false, !newyork.isEmpty());

    // command
    UserEvent result = userAggregate.handle(new DeleteUserCommand(contact.getAggregateId()));

  }

}