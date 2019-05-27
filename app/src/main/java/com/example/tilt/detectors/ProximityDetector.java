package com.example.tilt.detectors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

public class ProximityDetector implements Detector {
    private int SENSOR_SENSITIVITY;


    public ProximityDetector(int sensitivity){
        this.SENSOR_SENSITIVITY = sensitivity;
    }

    @Override
    public int detectEvent(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                return SUCCESS;
            }
            return UPDATE;
        }
        return FAIL;
    }


    @Override
    public String getValue() {
        return "UNLOCKED";
    }
}
