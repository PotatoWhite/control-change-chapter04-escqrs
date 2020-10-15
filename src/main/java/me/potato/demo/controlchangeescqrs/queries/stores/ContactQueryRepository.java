package me.potato.demo.controlchangeescqrs.queries.stores;

import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactQueryRepository extends CrudRepository<Contact, Long> {
}
