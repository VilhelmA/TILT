package com.example.tilt;

import android.hardware.SensorEvent;

public interface Detector {

    boolean detectEvent(SensorEvent event);
}
