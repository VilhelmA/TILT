package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

public class OrientationDetector implements Detector {

    /**
     * public static orientations for quick creation.
     * Example: new OrientationDetector(OrientationDetector.UPSIDEDOWN);
     */
    public final static float[] UPSIDEDOWN = {0, 0, -10};
    public final static float[] RIGHTSIDEUP = {0, 0, 10};
    public final static float[] RIGHT90DEG = {-10, 0, 0};
    public final static float[] LEFT90DEG = {10, 0, 0};
    public final static float[] VERTICAL = {0, 10, 0};
    public final static float[] VERTICALUPSIDEDOWN = {0, -10, 0};


    private float[] successOrientation;
    private double accuracy;
    private float[] currOrientation = {0,0,0};
    /**
     * Creates a new OrientationDetector.
     * @param successOrientation, either a custom float[] or one predefined.
     * @param accuracy, the accuracy of detection.
     */

    public OrientationDetector(float[] successOrientation, double accuracy){
        this.successOrientation = successOrientation;
        this.accuracy =  accuracy;
    }

    @Override
    public boolean detectEvent(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            this.currOrientation = event.values;
            if(isAccurate(event.values[0],successOrientation[0]) && isAccurate(event.values[1], successOrientation[1]) && isAccurate(event.values[2], successOrientation[2])){
                Log.e("ACC", "ACCURATE");
                return true;
            }
            Log.e("ACC", "NOT ACCURATE");
        }
        return false;
    }

    /**
     * Checks if c is between the accuracy margin of r.
     * That is to say c > r-acc && c < acc + r.
     * @param c, the value that is to be checked.
     * @param r, the value to be checked against.
     * @return
     */
    private boolean isAccurate(float c, float r){
        if(c >= r - this.accuracy && c <= r + this.accuracy){
            return true;
        }
        return false;
    }

    @Override
    public void configure() {
        Log.e("STAGE", "ORIENTATION");
        // no need to configure.
    }

    @Override
    public String getValue() {
        return "Current orientation:\n\t " + currOrientation[0] + " " + currOrientation[1] + " " + currOrientation[2];
    }
}