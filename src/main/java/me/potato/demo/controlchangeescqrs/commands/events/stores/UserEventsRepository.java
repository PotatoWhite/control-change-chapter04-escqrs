package me.potato.demo.controlchangeescqrs.commands.events.stores;


import me.potato.demo.controlchangeescqrs.commands.events.stores.entities.UserEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserEventsRepository extends CrudRepository<UserEvent, Long> {
  List<UserEvent> findAllByAggregateId(String aggregateId);
}
