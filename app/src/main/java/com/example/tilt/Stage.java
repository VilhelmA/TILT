package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import java.util.List;


public class Stage {
    private String sound;
    private String display;
    private SensorEvent solution;
    private List<SensorEvent> failures;
    private List<Sensor> sensors;
    private int fail;


    /**
     *
     * @param sound
     * @param display
     * @param solution
     * @param failures
     * @param sensors
     * @param fail
     */
    public Stage(String sound, String display, SensorEvent solution, List<SensorEvent> failures, List<Sensor> sensors, int fail){
        this.sound = sound;
        this.display = display;
        this.solution = solution;
        this.failures = failures;
        this.fail = fail;
        this.sensors = sensors;
        this.onCreate();
    }
    /**
     * Tries to solve the stage.
     * @param event, the SensorEvent, should always be evaluated if it is the correct
     * @return int, 1 if successful, 0, if nothing happened, -X if unsuccessful, where X is the number of steps backwards.
     */
    public int solve(SensorEvent event){
        if(this.isCorrectEvent(event)){
            for(SensorEvent e : failures){
                if(e.equals(event)){
                    return fail;
                }
            }
            if(event.equals(this.solution)){
                return 1;
            }
        }

        return 0;
    }

    /**
     * Checks if the SensorEvent is one of the valid types, that is to say, either a failure or a success.
     * @param e, the SensorEvent that is to be evaluated.
     * @return true, if the SensorEvent is a valid event. False, if it is not.
     */
    protected boolean isCorrectEvent(SensorEvent e){
        for (SensorEvent f : failures) {
            if(f.getClass().equals(e.getClass())){
                return true;
            }
        }

        if(solution.getClass().equals(e.getClass())){
            return true;
        }

        return false;
    }

    // TODO: Make this thing work. Might have to move it or make it public.
    private void onCreate(){
        // Set the stage's display.
        // Play the sound.
    }
}
