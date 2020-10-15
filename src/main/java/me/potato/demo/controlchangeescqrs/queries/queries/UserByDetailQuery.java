package me.potato.demo.controlchangeescqrs.queries.queries;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class UserByDetailQuery {
  private String aggregateId;
  private String detail;
}
