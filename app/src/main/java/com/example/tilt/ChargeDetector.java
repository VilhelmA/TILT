package com.example.tilt;

import android.content.Context;
import android.hardware.SensorEvent;
import android.os.BatteryManager;

public class ChargeDetector implements Detector {
    Context context;

    public ChargeDetector(Context context){
        this.context = context;
    }

    @Override
    public int detectEvent(SensorEvent event) {
        if(((BatteryManager) context.getSystemService(Context.BATTERY_SERVICE)).isCharging()){
            return SUCCESS;
        }
        return FAIL;
    }

    @Override
    public String getValue() {
        return null;
    }
}
