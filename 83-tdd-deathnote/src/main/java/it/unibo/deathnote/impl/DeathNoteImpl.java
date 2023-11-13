package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DeathNoteImpl implements DeathNote {

  public static final String DEFAULT_DEATH_CAUSE = "heart attack";

  private class Death {

    private String cause = DEFAULT_DEATH_CAUSE;

    private String details = "";

    public void setCause(final String cause) {
      this.cause = cause;
    }

    public void setDetails(final String details) {
      this.details = details;
    }
  }

  public static final int MS_TO_WRITE_DEATH_CAUSE = 40;
  public static final int MS_TO_WRITE_DEATH_DETAILS = 6040;
  private final Map<String, Death> data = new HashMap<>();
  private String latestName;
  private final TimerImpl timer = new TimerImpl();

  @Override
  public String getRule(final int ruleNumber) {
    final int numberOfRules = DeathNote.RULES.size();
    if (ruleNumber > numberOfRules || ruleNumber <= 0) {
      throw new IllegalArgumentException(
          "rule number must be smaller than 1 or larger than the number of rules (" + numberOfRules + ")");
    }
    return DeathNote.RULES.get(ruleNumber - 1);
  }

  @Override
  public void writeName(final String name) {
    Objects.requireNonNull(name,"name cannot be null");
    latestName = name;
    data.put(latestName, new Death());
    timer.reset();
  }

  @Override
  public boolean writeDeathCause(final String cause) {
    if (this.latestName == null || cause == null) {
      throw new IllegalStateException("there is no name written in this DeathNote or the cause is null");
    }
    final long timeElapsed = timer.partial(); // keep the timer running because the details can still be added.
    if (timeElapsed > MS_TO_WRITE_DEATH_CAUSE) {
      return false;
    }
    data.get(latestName).setCause(cause);
    return true;
  }

  @Override
  public boolean writeDetails(final String details) {
    if (this.latestName == null || details == null) {
      throw new IllegalStateException("there is no name written in this DeathNote or the cause is null");
    }
    final long timeElapsed = timer.stop();
    if (timeElapsed > MS_TO_WRITE_DEATH_DETAILS) {
      return false;
    }
    data.get(latestName).setDetails(details);
    return true;
  }

  @Override
  public String getDeathCause(final String name) {
    final Death death = data.get(name);
    if (death == null) {
      throw new IllegalArgumentException("The name " + name + " is not in the deathnote");
    }
    return death.cause;
  }

  @Override
  public String getDeathDetails(String name) {
    final Death death = data.get(name);
    if (death == null) {
      throw new IllegalArgumentException("The name " + name + " is not in the deathnote");
    }
    return death.details;
  }

  @Override
  public boolean isNameWritten(String name) {
    return data.containsKey(name);
  }
}