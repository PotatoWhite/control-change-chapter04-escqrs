package me.potato.demo.controlchangeescqrs.commands.commands;

import lombok.Getter;
import lombok.Setter;
import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Address;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class AddAddressesCommand extends Command {
  private Set<Address> addresses = new LinkedHashSet<>();

  public AddAddressesCommand(String aggregateId, Set<Address> addresses) {
    super(aggregateId);
    this.addresses = addresses;
  }
}
