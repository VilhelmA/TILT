package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class testGameActivity extends Game {
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_game);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        stageList = new ArrayList<Stage>();
        StageBuilder builder = new StageBuilder();
        builder.fail(0).solution(new ShakeDetector());
        Stage s = new Stage(null, null, new ShakeDetector(), new ArrayList<Detector>(), 0);
        stageList.add(s);
        this.start();
    }

    public void end(View v){
        this.end();
        TextView tv = findViewById(R.id.textView);
        tv.setText(currstage+"");
        this.start();
    }
    @Override
    public void start() {
        Log.d("MYTAG", "STARTING STAGE");
        this.sk.start();
    }

    @Override
    public void end() {
        this.sk.end();
    }
}
