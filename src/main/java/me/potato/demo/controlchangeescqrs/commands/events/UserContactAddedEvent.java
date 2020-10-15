package me.potato.demo.controlchangeescqrs.commands.events;

import com.google.gson.Gson;
import lombok.*;
import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Contact;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserContactAddedEvent extends Event {
  private Set<Contact> contacts;

  public UserContactAddedEvent(String aggregateId, Set<Contact> contacts) {
    super(aggregateId);
    this.contacts = contacts;
  }

  @Override
  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
