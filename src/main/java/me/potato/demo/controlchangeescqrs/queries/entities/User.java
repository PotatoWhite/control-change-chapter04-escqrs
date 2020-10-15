package me.potato.demo.controlchangeescqrs.queries.entities;

import lombok.*;
import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Address;
import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Contact;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class User {

  @Id
  @NonNull
  private String aggregateId;

  @NonNull
  private String account;
  @NonNull
  private String name;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "user")
  private Set<Contact> contacts = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "user")
  private Set<Address> addresses = new HashSet<>();

  public void addAddress(Address newAddress) {
    if (!addresses.contains(newAddress)) {
      newAddress.setUser(this);
      addresses.add(newAddress);
    }
  }

  public void addContact(Contact newContact) {
    if (!contacts.contains(newContact)) {
      newContact.setUser(this);
      contacts.add(newContact);
    }
  }
}
