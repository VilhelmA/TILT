package com.example.tilt.detectors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

public class LightDetector implements Detector {


    public int target;

    public LightDetector(int minimumTarget){
        this.target = minimumTarget;
    }

    @Override
    public int detectEvent(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            Log.d("LIGHT VALUE", ""+ event.values[0]);
            if (event.values[0] >= target) {
                return SUCCESS;
            }
            return UPDATE;
        }
        return FAIL;
    }


    @Override
    public String getValue() {
        return null;
    }
}
