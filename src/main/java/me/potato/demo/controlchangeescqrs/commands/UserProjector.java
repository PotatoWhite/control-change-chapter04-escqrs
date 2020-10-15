package me.potato.demo.controlchangeescqrs.commands;

import me.potato.demo.controlchangeescqrs.commands.events.EventHandler;
import me.potato.demo.controlchangeescqrs.queries.stores.UserQueryRepository;
import org.springframework.stereotype.Component;


@Component
public class UserProjector {

  private final UserQueryRepository userQueryRepository;

  private final EventHandler eventHandler;

  public UserProjector(UserQueryRepository userQueryRepository, EventHandler eventHandler) {
    this.userQueryRepository = userQueryRepository;
    this.eventHandler        = eventHandler;
  }

  public void project(String aggregateId) throws Exception {
    userQueryRepository.save(eventHandler.replayAllEvents(aggregateId));
  }

  public void remove(String aggregateId) {
    userQueryRepository.deleteById(aggregateId);
  }
}
