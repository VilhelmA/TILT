package com.example.tilt;

import android.hardware.SensorEvent;

public interface Detector {
    int SUCCESS = 1;
    int FAIL = -1;
    int UPDATE = 10;
    int detectEvent(SensorEvent event);


    String getValue();
}
