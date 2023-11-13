package it.unibo.deathnote.api;

public interface Timer {
  /**
   * Starts (or restarts) the timer.
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
   * Take a partial time. Does not stop the timer. 
   * 
   * @return the time elapsed (in milliseconds) since start() was called
   * @throws IllegalStateException if the timer is not running.
   */
  long partial();
}
