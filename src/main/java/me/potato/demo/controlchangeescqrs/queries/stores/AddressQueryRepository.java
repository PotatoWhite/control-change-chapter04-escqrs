package me.potato.demo.controlchangeescqrs.queries.stores;

import me.potato.demo.controlchangeescqrs.queries.entities.valueobjects.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface AddressQueryRepository extends CrudRepository<Address, Long> {
  Set<Address> findAllByCity(String city);
}
