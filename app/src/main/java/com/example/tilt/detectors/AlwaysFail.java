package com.example.tilt.detectors;

import android.hardware.SensorEvent;

import com.example.tilt.detectors.Detector;

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
