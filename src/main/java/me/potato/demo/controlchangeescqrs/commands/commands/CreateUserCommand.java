package me.potato.demo.controlchangeescqrs.commands.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserCommand extends Command {
  private String account;
  private String name;

  public CreateUserCommand(String account, String name) {
    this.account = account;
    this.name    = name;
  }
}
