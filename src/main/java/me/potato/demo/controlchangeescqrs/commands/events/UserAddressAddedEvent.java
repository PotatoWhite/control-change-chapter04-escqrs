package me.potato.demo.controlchangeescqrs.commands.events;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Address;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserAddressAddedEvent extends Event {
  private Set<Address> addresses;

  public UserAddressAddedEvent(String aggregateId, Set<Address> addresses) {
    super(aggregateId);
    this.addresses = addresses;
  }

  @Override
  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
