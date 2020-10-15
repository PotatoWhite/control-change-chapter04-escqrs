package me.potato.demo.controlchangeescqrs.commands.events;

import me.potato.demo.controlchangeescqrs.commands.events.stores.UserEventsRepository;
import me.potato.demo.controlchangeescqrs.commands.events.stores.entities.UserEvent;
import me.potato.demo.controlchangeescqrs.queries.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventHandler {
  private final UserEventsRepository userEventsRepository;

  public EventHandler(UserEventsRepository userEventsRepository) {
    this.userEventsRepository = userEventsRepository;
  }

  public User replayAllEvents(String aggregateId) throws ClassNotFoundException {
    List<UserEvent> userEvents = userEventsRepository.findAllByAggregateId(aggregateId);

    User user = null;

    for (var userEvent : userEvents) {
      user = translateAndDispatchEvent(aggregateId, user, userEvent);
    }

    return user;
  }

  private User translateAndDispatchEvent(String aggregateId, User user, UserEvent userEvent) throws ClassNotFoundException {
    if (userEvent.getEvent() instanceof UserCreatedEvent) {
      user = onUserCreatedEvent(aggregateId, (UserCreatedEvent) userEvent.getEvent());
    } else if (userEvent.getEvent() instanceof UserAddressAddedEvent) {
      user = onUserAddressAddedEvent(user, (UserAddressAddedEvent) userEvent.getEvent());
    } else if (userEvent.getEvent() instanceof UserContactAddedEvent) {
      user = onUserContactAddedEvent(user, (UserContactAddedEvent) userEvent.getEvent());
    } else if (userEvent.getEvent() instanceof UserDeletedEvent) {
      user = onUserDeletedEvent(user, (UserDeletedEvent) userEvent.getEvent());
    }
    return user;
  }

  // UserCreatedEvent
  private User onUserCreatedEvent(String aggregateId, UserCreatedEvent event) {
    return new User(aggregateId, event.getAccount(), event.getName());
  }

  // UserAddressPutEvent
  private User onUserAddressAddedEvent(User user, UserAddressAddedEvent event) {
    event.getAddresses().stream().forEach(user::addAddress);
    return user;
  }

  // UserContactPutEvent
  private User onUserContactAddedEvent(User user, UserContactAddedEvent event) {
    event.getContacts().stream().forEach(user::addContact);
    return user;
  }

  // UserContactPutEvent
  private User onUserDeletedEvent(User user, UserDeletedEvent event) {
    return null;
  }
}
