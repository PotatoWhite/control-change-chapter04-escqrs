package me.potato.demo.controlchangeescqrs.commands.events.stores.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventPayload {
  private String type;
  private String payload;
}
