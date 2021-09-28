package org.zhupanovdm.bsl;

import javax.annotation.CheckForNull;

public class Issue {
  private final Integer line;
  private final String message;
  private final Double cost;

  private Issue(Integer line, String message, Double cost) {
    this.line = line;
    this.message = message;
    this.cost = cost;
  }

  @CheckForNull
  public Integer line() {
    return line;
  }

  public String message() {
    return message;
  }

  @CheckForNull
  public Double cost() {
    return cost;
  }

  public static Issue fileIssue(String message) {
    return new Issue(null, message, null);
  }

  public static Issue lineIssue(int line, String message) {
    return new Issue(line, message, null);
  }

  public static Issue lineIssue(int line, String message, double cost) {
    return new Issue(line, message, cost);
  }
}
