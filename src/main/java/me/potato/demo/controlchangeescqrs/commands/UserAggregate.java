package me.potato.demo.controlchangeescqrs.commands;

import me.potato.demo.controlchangeescqrs.commands.commands.CreateUserCommand;
import me.potato.demo.controlchangeescqrs.commands.commands.DeleteUserCommand;
import me.potato.demo.controlchangeescqrs.commands.commands.AddAddressesCommand;
import me.potato.demo.controlchangeescqrs.commands.commands.AddContactsCommand;
import me.potato.demo.controlchangeescqrs.commands.events.*;
import me.potato.demo.controlchangeescqrs.commands.events.stores.UserEventsRepository;
import me.potato.demo.controlchangeescqrs.commands.events.stores.entities.EventPayload;
import me.potato.demo.controlchangeescqrs.commands.events.stores.entities.UserEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAggregate {

  private final UserEventsRepository userEventsRepository;
  private final UserProjector        projector;

  public UserAggregate(UserEventsRepository userEventsRepository, UserProjector projector) {
    this.userEventsRepository = userEventsRepository;
    this.projector            = projector;
  }

  public UserEvent handle(CreateUserCommand command) throws Exception {
    return storeAndProject(new UserCreatedEvent(UUID.randomUUID().toString(), command.getAccount(), command.getName()));
  }

  public UserEvent handle(DeleteUserCommand command) throws Exception {
    return storeAndRemove(new UserDeletedEvent(command.getAggregateId()));
  }

  public UserEvent handle(AddAddressesCommand command) throws Exception {
    return storeAndProject(new UserAddressAddedEvent(command.getAggregateId(), command.getAddresses()));
  }

  public UserEvent handle(AddContactsCommand command) throws Exception {
    return storeAndProject(new UserContactAddedEvent(command.getAggregateId(), command.getContacts()));
  }

  private UserEvent storeAndProject(Event event) throws Exception {
    UserEvent userEvent = new UserEvent(event.getAggregateId(), new EventPayload(event.getClass().getName(), event.toJson()));
    UserEvent save      = userEventsRepository.save(userEvent);
    projector.project(save.getAggregateId());
    return save;
  }

  private UserEvent storeAndRemove(Event event) throws Exception {
    UserEvent userEvent = new UserEvent(event.getAggregateId(), new EventPayload(event.getClass().getName(), event.toJson()));
    UserEvent save      = userEventsRepository.save(userEvent);
    projector.remove(save.getAggregateId());
    return save;
  }

}
