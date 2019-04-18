package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.util.Observable;


public class testGameActivity extends Game {
    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor magnoSensor;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_game);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnoSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnoSensor, SensorManager.SENSOR_DELAY_GAME);

        StageBuilder builder = new StageBuilder();
//        stageList.add(builder.fail(0).solution(new OrientationDetector(OrientationDetector.UPSIDEDOWN, 1)).build());
//        stageList.add(builder.fail(0).solution(new OrientationDetector(OrientationDetector.VERTICAL, 1)).build());
//        stageList.add(builder.fail(0).solution(new OrientationDetector(OrientationDetector.LEFT90DEG, 1)).build());

        stageList.add(builder.fail(0).sound("hello").solution(new OrientationDetector(OrientationDetector.UPSIDEDOWN, 1)).build());
        stageList.add(builder.fail(0).sound("shake").solution(new ShakeDetector()).build());

        //stageList.add(builder.fail(0).sound(Uri.fromFile(new File("app/res/raw/shake"))).solution(new AngleDetector(50, 3)).build());
        //stageList.add(builder.fail(0).sound(Uri.fromFile(new File("app/res/raw/shake"))).solution(new ShakeDetector()).build());
        //stageList.add(builder.fail(0).sound(Uri.fromFile(new File("app/res/raw/shake"))).solution(new AngleDetector(0, 5)).build());

        for(Stage s : stageList){
            s.addObserver(this);
        }
        this.start();
    }

    @Override
    public void start() {
        this.sk.start();
    }

    @Override
    public void end() {
        TextView tv = findViewById(R.id.textView);
        tv.setText("time :" + this.sk.end().toString());

    }

    @Override
    public void update(Observable observable, Object o) {
        TextView tv = findViewById(R.id.textView);
        tv.setText(currStage.solutionValue());

        if(o == "CREATED"){ // Play only on create
            mp = MediaPlayer.create(this, currStage.sound());
            mp.start();
        }

    }
}
