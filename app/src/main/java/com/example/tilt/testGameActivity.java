package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Observable;


public class testGameActivity extends Game {
    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor magnoSensor;
    private Sensor rotSensor;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_game);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnoSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        rotSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnoSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, rotSensor, SensorManager.SENSOR_DELAY_GAME);

        StageBuilder builder = new StageBuilder();
        stageList.add(builder.fail(0).display("standard").startSound("hello").playSound("shake").
                solution(new AngleDetector(200,1)).build());
        stageList.add(builder.fail(0).display("standard").startSound("hello").
                solution(new OrientationDetector(OrientationDetector.VERTICALUPSIDEDOWN, 1)).build());
        stageList.add(builder.fail(0).display("below").startSound("shake").
                solution(new ShakeDetector()).build());


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
    public void end() { }

    @Override
    public void update(Observable observable, Object o) {

        if(o == "CREATED"){ // Play only on create
            mp = MediaPlayer.create(this, currStage.sound());
            ImageView image = findViewById(R.id.img);
            image.setImageURI(currStage.display());
            mp.start();
            mp.stop();
        }
        if(o == "CHANGED"){
            mp = MediaPlayer.create(this, currStage.playSound());
            mp.start();
        }

    }
}
