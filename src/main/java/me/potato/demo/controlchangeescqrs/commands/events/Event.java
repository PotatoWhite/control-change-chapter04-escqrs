package me.potato.demo.controlchangeescqrs.commands.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Event implements Serializable {
  private String        id      = UUID.randomUUID().toString();
  private LocalDateTime created = LocalDateTime.now();
  private String        aggregateId;

  public Event(String aggregateId) {
    this.aggregateId = aggregateId;
  }

  public abstract String toJson();
}
