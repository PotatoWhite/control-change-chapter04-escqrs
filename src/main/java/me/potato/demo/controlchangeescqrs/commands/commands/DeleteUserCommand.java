package me.potato.demo.controlchangeescqrs.commands.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserCommand extends Command {
  public DeleteUserCommand(String aggregateId) {
    super(aggregateId);
  }
}
