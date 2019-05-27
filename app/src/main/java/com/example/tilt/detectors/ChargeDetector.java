package com.example.tilt.detectors;

import android.content.Context;
import android.content.IntentFilter;
import android.hardware.SensorEvent;
import android.os.BatteryManager;


public class ChargeDetector implements Detector {
    Context context;

    public ChargeDetector(Context context){
        this.context = context;
    }

    @Override
    public int detectEvent(SensorEvent event) {
        if(((BatteryManager) this.context.getSystemService(Context.BATTERY_SERVICE)).isCharging() || context.registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE")).getExtras().getBoolean("connected")){
            return SUCCESS;
        }
        return FAIL;
    }

    @Override
    public String getValue() {
        return null;
    }
}
