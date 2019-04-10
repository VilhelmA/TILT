package com.example.tilt;

import java.time.Duration;
import java.time.Instant;

public class ScoreKeeper {
    private Instant startTime;
    private int noOfErrors;

    /**
     * Starts tracking time and and number of errors. Resets the error tracker.
     */
    public void start(){
        this.startTime = Instant.now(); // API level warning
        this.noOfErrors = 0;
    }

    /**
     *
     * @return Duration, the duration which the player tried to solve the puzzle.
     */
    public Duration end(){
        return Duration.between(this.startTime, Instant.now()); // API level warnings
    }

    /**
     * Returns the number of errors,
     * @return the number of errors, in the last or current game.
     */
    public int getNoOfErrors(){
        return noOfErrors;
    }

}
