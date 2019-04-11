package com.example.tilt;

import android.hardware.SensorEvent;
import java.util.List;


public class Stage {
    private String sound;
    private String display;
    private Detector solution;
    private List<Detector> failures;
    private int fail;

    /**
     *
     * @param sound
     * @param display
     * @param solution
     * @param failures
     * @param fail
     */
    public Stage(String sound, String display, Detector solution, List<Detector> failures, int fail){
        this.sound = sound;
        this.display = display;
        this.solution = solution;
        this.failures = failures;
        this.fail = fail;
        this.onCreate();
    }
    /**
     * Tries to solve the stage.
     * @param event, the SensorEvent, should always be evaluated if it is the correct
     * @return int, 1 if successful, 0, if nothing happened, -X if unsuccessful, where X is the number of steps backwards.
     */
    public int solve(SensorEvent event){
        if(solution.detectEvent(event)) {
            return 1;
        }

        for (Detector d: failures) {
            if(d.detectEvent(event)) {
                return -fail;
            }
        }

        return 0;
    }

    // TODO: Make this thing work. Might have to move it or make it public.
    public void onCreate(){
        solution.configure();
        // Configure the solution, e.g. set current direction and such.
        // Set the stage's display.
        // Play the sound.

    }
}
