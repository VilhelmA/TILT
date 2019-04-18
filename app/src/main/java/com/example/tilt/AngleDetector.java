package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

public class AngleDetector implements Detector {
    private float lastRot;
    private boolean start = false;
    private float totalRot;
    private float successDeg;
    private int accuracy;

    /**
     * Class for detecting if the phone has been pointed in the correct angle.
     * @param successDeg, the angle which we should look for.
     * @param accuracy,
     */
    public AngleDetector(float successDeg, int accuracy) {
        this.successDeg = successDeg;
        this.accuracy = accuracy;
    }

    // TODO: THIS DOESNT WORK AS INTENDED, SOMETIMES GIVE LARGE NEGATIVE ANSWER INSTEAD.
    @Override
    public boolean detectEvent(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){ // Deprecated, might still work?
            if(!this.start){ // Only runs on first event to set reference.
                this.start = true;
                this.lastRot = Math.round(event.values[0]);
                return false;
            }
            float dRot = convertValue(event.values[0], lastRot);
            lastRot = event.values[0];
            Log.d("DETECTOR", "ROTATED: " + dRot);
            totalRot += dRot;
            Log.d("TOTAL ROT", ""+ totalRot);
            if(totalRot >= successDeg - accuracy && totalRot <= successDeg + accuracy){
                Log.d("DETECTOR", "DIR IN IF-CASE");
                return true;
            }
        }
        return false;
    }

    private float convertValue(float val, float lastRot){
            return Math.round(val - lastRot);
}

    @Override
    public void configure() {
        //Log.d("STARTED STAGE", "ANGLE");
    }

    @Override
    public String getValue() {
        return "CURRENT ANGLE: " + this.totalRot;
    }
}
