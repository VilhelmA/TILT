package com.example.tilt;

import android.hardware.SensorEvent;

/**
 * Class to hot fix
 */
public class AlwaysFail implements Detector {
    @Override
    public int detectEvent(SensorEvent event) {
        return FAIL;
    }

    @Override
    public String getValue() {
        return "FAIL";
    }
}
