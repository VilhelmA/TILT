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
        Instant now = Instant.now();
        return Duration.between(this.startTime, now); // API level warnings
    }

    /**
     * Increases the number of errors with X
     * @param x
     */
    public void increase(int x){
        this.noOfErrors = this.noOfErrors + x;
    }

    /**
     * Returns the number of errors,
     * @return the number of errors, in the last or current game.
     */
    public int getNoOfErrors(){
        return noOfErrors;
    }

}
