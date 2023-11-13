package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.Timer;

public class TimerImpl implements Timer {

  private long startTime;
  private boolean isRunning;

  @Override
  public void reset() {
    isRunning = true;
    startTime = System.currentTimeMillis();
  }

  @Override
  public long partial() {
    if (!isRunning) {
      throw new IllegalStateException("timer is not running");
    }
    long endTime = System.currentTimeMillis();
    return endTime - startTime;
  }

  @Override
  public long stop() {
    long out = partial();
    isRunning = false;
    return out;
  }
}
