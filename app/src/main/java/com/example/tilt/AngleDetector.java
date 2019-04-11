package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

public class AngleDetector implements Detector {
    private float startDeg;
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

    @Override
    public boolean detectEvent(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            float dir = event.values[0] - startDeg;
            Log.d("DETECTOR", "CURRENT ANGLE: " + dir);
            if(dir >= successDeg - accuracy && dir <= successDeg + accuracy){
                Log.d("DETECTOR", "DIR IN IF-CASE");
                return true;
            }
        }
        return false;
    }

    @Override
    public void configure() {
        Log.d("STARTED STAGE", "ANGLE");
    }
}
