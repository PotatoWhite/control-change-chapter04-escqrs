package me.potato.demo.controlchangeescqrs.commands.events;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDeletedEvent extends Event {
  public UserDeletedEvent(String aggregateId) {
    super(aggregateId);
  }

  @Override
  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
