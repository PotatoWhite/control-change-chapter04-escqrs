package me.potato.demo.controlchangeescqrs.commands.commands;

import lombok.Getter;
import lombok.Setter;
import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Contact;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class AddContactsCommand extends Command {
  private Set<Contact> contacts = new LinkedHashSet<>();

  public AddContactsCommand(String aggregateId, Set<Contact> contacts) {
    super(aggregateId);
    this.contacts = contacts;
  }
}
