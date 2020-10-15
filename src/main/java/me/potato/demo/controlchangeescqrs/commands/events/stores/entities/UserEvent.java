package me.potato.demo.controlchangeescqrs.commands.events.stores.entities;

import com.google.gson.Gson;
import lombok.*;
import me.potato.demo.controlchangeescqrs.commands.events.Event;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(indexes = {@Index(name = "aggregate_id", columnList = "aggregateId")})
public class UserEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime created = LocalDateTime.now();

  @NonNull
  private String aggregateId;

  @NonNull
  @Lob
  @Convert(converter = EventPayloadConverter.class)
  private EventPayload eventPayload;

  public Event getEvent() throws ClassNotFoundException {
    Gson gson = new Gson();
    return gson.fromJson(eventPayload.getPayload(), (Type) Class.forName(eventPayload.getType()));
  }
}
