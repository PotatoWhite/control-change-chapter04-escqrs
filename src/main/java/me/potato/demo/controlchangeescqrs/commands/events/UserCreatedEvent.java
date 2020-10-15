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
public class UserCreatedEvent extends Event {
  private String account;
  private String name;

  public UserCreatedEvent(String aggregateId, String account, String name) {
    super(aggregateId);
    this.account = account;
    this.name    = name;
  }

  @Override
  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
