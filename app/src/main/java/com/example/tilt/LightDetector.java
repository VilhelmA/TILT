package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

public class LightDetector implements Detector {

    public int SENSOR_SENSITIVITY;
    public int target;

    public LightDetector(int target, int sens){
        this.target = target;
        this.SENSOR_SENSITIVITY = sens;
    }

    @Override
    public int detectEvent(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            Log.d("LIGHT VALUE", ""+ event.values[0]);
            if (event.values[0] >= -SENSOR_SENSITIVITY +target && event.values[0] <= SENSOR_SENSITIVITY+ target) {
                return SUCCESS;
            }
            return UPDATE;
        }
        return FAIL;
    }

    @Override
    public void configure() {

    }

    @Override
    public String getValue() {
        return null;
    }
}
