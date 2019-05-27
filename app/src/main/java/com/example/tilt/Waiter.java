package com.example.tilt;

import android.hardware.SensorEvent;
import android.os.Handler;
import android.util.Log;

import com.example.tilt.detectors.Detector;


public class Waiter implements Detector {

    private int time;

    public Waiter(int timeInMillis){
        this.time = timeInMillis;
    }

    @Override
    public int detectEvent(SensorEvent event) {
        Log.d("SLEEPO", "BEEPO");
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                // DO nothing;
            }
        }, this.time);

        return SUCCESS;
    }

    @Override
    public String getValue() {
        return "GOING TO SLEEP";
    }
}
