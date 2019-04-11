package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



public class testGameActivity extends Game {
    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor magnoSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_game);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnoSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnoSensor, SensorManager.SENSOR_DELAY_GAME);

        StageBuilder builder = new StageBuilder();
        stageList.add(builder.fail(0).solution(new AngleDetector(0, 3)).build());
        stageList.add(builder.fail(0).solution(new ShakeDetector()).build());
        stageList.add(builder.fail(0).solution(new AngleDetector(0, 5)).build());
    }

    @Override
    public void start() {
        this.sk.start();
    }

    @Override
    public void end() {
        this.sk.end();
    }
}
