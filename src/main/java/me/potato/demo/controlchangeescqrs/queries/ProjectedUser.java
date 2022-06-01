package me.potato.demo.controlchangeescqrs.queries;

import me.potato.demo.controlchangeescqrs.queries.entities.User;
import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Address;
import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Contact;
import me.potato.demo.controlchangeescqrs.queries.queries.UserByCityQuery;
import me.potato.demo.controlchangeescqrs.queries.queries.UserByDetailQuery;
import me.potato.demo.controlchangeescqrs.queries.queries.UsersByCityQuery;
import me.potato.demo.controlchangeescqrs.queries.stores.AddressQueryRepository;
import me.potato.demo.controlchangeescqrs.queries.stores.UserQueryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectedUser {
  private final UserQueryRepository    userQueryRepository;
  private final AddressQueryRepository addressQueryRepository;

  public ProjectedUser(UserQueryRepository userQueryRepository, AddressQueryRepository addressQueryRepository) {
    this.userQueryRepository    = userQueryRepository;
    this.addressQueryRepository = addressQueryRepository;
  }

  public User handle(UserByCityQuery query) throws Exception {
    Optional<User> byId      = userQueryRepository.findById(query.getAggregateId());
    Set<Address>   addresses = byId.map(user -> user.getAddresses()).orElseThrow(() -> new Exception("User does not exist."));
    Set<Address>   collect   = addresses.stream().filter(address -> address.getCity().equals(query.getCity())).collect(Collectors.toSet());
    if (collect.isEmpty())
      return null;

    return byId.get();
  }

  public User handle(UserByDetailQuery query) throws Exception {
    Optional<User> byId     = userQueryRepository.findById(query.getAggregateId());
    Set<Contact>   contacts = byId.map(user -> user.getContacts()).orElseThrow(() -> new Exception("User does not exist."));
    Set<Contact>   collect  = contacts.stream().filter(contact -> contact.getDetail().equals(query.getDetail())).collect(Collectors.toSet());
    if (collect.isEmpty())
      return null;

    return byId.get();
  }

  public Set<User> handle(UsersByCityQuery query) {
    Set<Address> allByCity = addressQueryRepository.findAllByCity(query.getCity());
    return allByCity.stream().map(address -> address.getUser()).collect(Collectors.toSet());
  }

}
