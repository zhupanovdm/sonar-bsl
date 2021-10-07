package org.zhupanovdm.bsl;

import lombok.Data;

import javax.annotation.Nonnull;
import java.util.Optional;

@Data
public class Issue {
  private final Integer line;
  private final String message;
  private final Double cost;
  private final Check check;

  public Issue(@Nonnull Check check, @Nonnull String message, Integer line, Double cost) {
    this.check = check;
    this.message = message;
    this.line = line;
    this.cost = cost;
  }

  public Optional<Integer> getLine() {
    return Optional.ofNullable(line);
  }

  public Optional<Double> getCost() {
    return Optional.ofNullable(cost);
  }
}
