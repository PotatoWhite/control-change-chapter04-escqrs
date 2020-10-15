package me.potato.demo.controlchangeescqrs.queries.entities.valueobjects;

import lombok.*;
import me.potato.demo.controlchangeescqrs.queries.entities.User;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "user"})
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Contact {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  private String type;

  @NonNull
  private String detail;

  @ManyToOne
  @JoinColumn(name = "aggregate_id")
  private User user;
}
