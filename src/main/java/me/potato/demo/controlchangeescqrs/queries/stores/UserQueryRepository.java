package me.potato.demo.controlchangeescqrs.queries.stores;

import me.potato.demo.controlchangeescqrs.queries.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserQueryRepository extends CrudRepository<User, String> {
}
