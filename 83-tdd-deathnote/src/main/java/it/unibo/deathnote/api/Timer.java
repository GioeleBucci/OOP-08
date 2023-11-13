package it.unibo.deathnote.api;

public interface Timer {
  /**
   * Re-starts the timer.
   */
  void reset();

  /**
   * Stops the timer.
   * 
   * @return the time elapsed (in milliseconds) since start() was called
   * @throws IllegalStateException if the timer is already stopped.
   */
  long stop();

  /**
   * Returns time elapsed since the timer started without stopping it
   * 
   * @throws IllegalStateException if the timer is not running.
   */
  long partial();
}
