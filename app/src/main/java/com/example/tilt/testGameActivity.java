package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Observable;


public class testGameActivity extends Game {
    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor magnoSensor;
    private Sensor rotSensor;
    private MediaPlayer mp;
    private boolean usingPlaySound = false;
    private static final int[] DISPLAYVALUE = {0, 1};

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
        stageList.add(builder.solution(new OrientationDetector(OrientationDetector.VERTICALUPSIDEDOWN, 3)).fail(0).display("front").startSound("hello").playSound("dialturnshort").build());
        stageList.add(builder.solution(new OrientationDetector(OrientationDetector.VERTICAL, 3)).fail(0).display("belowfullsize").build());
        stageList.add(builder.solution(new AngleDetector( 90, 5, 3)).fail(0).playSound("dialturnshort").display("front").build());
        stageList.add(builder.solution(new AngleDetector( 40, 5, 3)).failure(new AngleDetector(120, 5, 3)).fail(0).playSound("dialturnshort").startSound("safesuccess").display("front1").build());
        stageList.add(builder.solution(new AngleDetector( 40, 5, 3)).failure(new AngleDetector(30, 5, 3)).fail(1).playSound("dialturnshort").startSound("safesuccess").display("front2").build());
        stageList.add(builder.solution(new AngleDetector( 70, 5, 3)).failure(new AngleDetector(80, 5, 3)).fail(2).playSound("dialturnshort").startSound("safesuccess").display("front3").build());


        /*
        stageList.add(builder.fail(0).display("front").startSound("hello").playSound("dialturnshort")
                .solution(new AngleDetector(200,1, 2)).build());
        stageList.add(builder.fail(0).display("front").startSound("hello").
                solution(new OrientationDetector(OrientationDetector.VERTICALUPSIDEDOWN, 1)).build());
        stageList.add(builder.fail(0).display("belowfullsize").startSound("dialturnshort").
                solution(new ShakeDetector()).build());
"))*/

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
            if(mp != null){
                mp.release();
            }

            mp = MediaPlayer.create(this, currStage.sound());
            ImageView image = findViewById(R.id.img);
            image.setImageURI(currStage.display());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            TextView tv = findViewById(R.id.txtRot);
            for (int i : DISPLAYVALUE){
                if(i != this.index){
                    tv.setText("");
                }
            }
            usingPlaySound = false;
            mp.start();

        }
        if(o == "CHANGED"){
            TextView tv = findViewById(R.id.txtRot);
            tv.setZ(3);
            for (int i : DISPLAYVALUE){
                if(i == this.index){
                    tv.setText(currStage.solutionValue());
                }
            }
            if(mp.isPlaying()){

            }else{
                if(!usingPlaySound){
                    mp = MediaPlayer.create(this, currStage.playSound());
                    usingPlaySound = true;
                }
                mp.start();

            }
        }

    }
}
