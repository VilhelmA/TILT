package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

public class AngleDetector implements Detector {
    private float lastRot;
    private boolean start = false;
    private float totalRot;
    private float successDeg;
    private int accuracy;
    private int update;
    int mAzimuth;
    float[] rMat = new float[9];
    float[] orientation = new float[3];
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;

    /**
     * Class for detecting if the phone has been pointed in the correct angle.
     * @param successDeg, the angle which we should look for.
     * @param accuracy,
     */
    public AngleDetector(float successDeg, int accuracy, int update) {
        this.successDeg = successDeg;
        this.accuracy = accuracy;
        this.update = update;
    }

    @Override
    public int detectEvent(SensorEvent event) {
        if(!this.start && event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){ // Only runs on first event to set reference.
            this.start = true;
            this.lastRot = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0])+360) %360;
            Log.d("TEST", "STARTED");
            return FAIL;
        }
        if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rMat, event.values);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0])+360) %360;
        }

        mAzimuth = Math.round(mAzimuth);

        int dRot = Math.round(mAzimuth-lastRot);
        Log.d("Rot", "DROT: " + dRot + " mAZ: " + mAzimuth + " LAST: " + lastRot + " TOT: " +totalRot);
        lastRot = mAzimuth;
        totalRot += dRot;

        if(totalRot >= successDeg - accuracy && totalRot <= successDeg + accuracy){
            return SUCCESS;
        }

        if(Math.abs(dRot) >= update){
            return UPDATE;
        }

        return FAIL;
    }

    private float convertValue(float val, float lastRot){
            return Math.round(val - lastRot);
}

    @Override
    public void configure() {
        //Log.d("STARTED STAGE", "ANGLE");
    }

    @Override
    public String getValue() {
        return (int) this.totalRot + "";
    }
}
