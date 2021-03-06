package com.example.tilt.stages;

import android.hardware.SensorEvent;

import android.net.Uri;

import com.example.tilt.detectors.Detector;

import java.util.Observable;


public class Stage extends Observable {
    private Uri startSound, playSound;
    private Uri display;
    private Detector solution;
    private Detector failure;
    private int fail;
    private int playID;
    private int startID;
    protected static final int SUCCESS = 1;
    protected static final int FAIL = -1;
    protected static final int UPDATE = 10;

    /**
     * Stage
     * @param sound
     * @param display
     * @param solution
     * @param failure
     * @param fail
     */
    public Stage(Uri sound, Uri playSound, Uri display, Detector solution, Detector failure, int fail, int startID, int playID){
        this.startSound = sound;
        this.display = display;
        this.solution = solution;
        this.failure = failure;
        this.fail = fail;
        this.playSound = playSound;
        this.startID = startID;
        this.playID = playID;
        this.onCreate();
    }

    /**
     * Tries to solve the stage.
     * @param event, the SensorEvent, should always be evaluated if it is the correct
     * @return int, 1 if successful, 0, if nothing happened, -X if unsuccessful, where X is the number of steps backwards.
     */
    public int solve(SensorEvent event){
        int result = solution.detectEvent(event);
        switch (result){
            case SUCCESS:
                setChanged();
                notifyObservers("SUCCESS");
                return 1;

            case UPDATE:
                setChanged();
                notifyObservers("CHANGED");
                return 0;
        }
        if(failure != null){
            int r = failure.detectEvent(event);
            if(r == SUCCESS) {
                setChanged();
                notifyObservers();
                return -fail;
            }
        }

        return 0;
    }

    public void onCreate(){
        setChanged();
        notifyObservers("CREATED"); // Send "CREATED" as an argument to play sounds and set the display.
    }

    /**
     * Value of the solution, depends on the solution and therefore is a String.
     * @return String, return a String representation of the current value of the solution.
     */

    public String solutionValue(){
        return solution.getValue();
    }

    /**
     * Returns a URI to the sound that should be played.
     * @return URI, link to sound that should be played? Might have to change later.
     */
    public int sound(){
        return this.startID;
    }

    public int playSound(){
        return this.playID;
    }

    /**
     * Returns a URI to the image to display.
     * @return URI, link to the image that should be displayed.
     */
    public Uri display(){
        return this.display;
    }
}
