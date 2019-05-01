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
    public AngleDetector(float successDeg, int accuracy) {
        this.successDeg = successDeg;
        this.accuracy = accuracy;
    }

    @Override
    public boolean detectEvent(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rMat, event.values);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + 360) % 360;
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(rMat, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(rMat, orientation);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + 360);
        }

        mAzimuth = Math.round(mAzimuth);
        if(!this.start){ // Only runs on first event to set reference.
            this.start = true;
            this.lastRot = mAzimuth;
            Log.d("TEST", "STARTED");
            return false;
        }
        int dRot = Math.round(mAzimuth-lastRot);
        Log.d("Rot", "DROT: " + dRot + " mAZ: " + mAzimuth + " LAST: " + lastRot + " TOT: " +totalRot);
        lastRot = mAzimuth;
        totalRot += dRot;

        if(totalRot >= successDeg - accuracy && totalRot <= successDeg + accuracy){
            return true;
        }



        return false;
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
        return "CURRENT ANGLE: " + this.totalRot;
    }
}
