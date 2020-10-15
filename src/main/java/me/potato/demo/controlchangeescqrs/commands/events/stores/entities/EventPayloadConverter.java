package me.potato.demo.controlchangeescqrs.commands.events.stores.entities;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;

@Slf4j
public class EventPayloadConverter implements AttributeConverter<EventPayload, String> {
  private final Gson gson = new Gson();

  @Override
  public String convertToDatabaseColumn(EventPayload attribute) {
    return gson.toJson(attribute);
  }

  @Override
  public EventPayload convertToEntityAttribute(String dbData) {
    return gson.fromJson(dbData, EventPayload.class);
  }
}
