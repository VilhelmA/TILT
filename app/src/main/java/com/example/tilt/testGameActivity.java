package com.example.tilt;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Observable;


public class testGameActivity extends Game implements SoundPool.OnLoadCompleteListener {
    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor magnoSensor;
    private Sensor rotSensor;
    private MediaPlayer mp;
    private boolean usingPlaySound = false;
    private static final int[] DISPLAYVALUE = {2,3,4,5};
    private SoundPool sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_game);
        sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnoSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        rotSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnoSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, rotSensor, SensorManager.SENSOR_DELAY_GAME);

        StageBuilder builder = new StageBuilder();
        stageList.add(builder.solution(new OrientationDetector(OrientationDetector.VERTICALUPSIDEDOWN, 3)).fail(0).display("front").startSound(R.raw.hello).playSound(R.raw.dialturnshort).build());
        stageList.add(builder.solution(new OrientationDetector(OrientationDetector.VERTICAL, 3)).fail(0).display("belowfullsize").build());
        stageList.add(builder.solution(new AngleDetector( 90, 5, 1)).fail(0).playSound(R.raw.dialturnshort).display("front").build());
        stageList.add(builder.solution(new AngleDetector( 40, 5, 1)).failure(new AngleDetector(120, 5, 3)).fail(0).playSound(R.raw.dialturnshort).startSound(R.raw.safesuccess).display("front1").build());
        stageList.add(builder.solution(new AngleDetector( 40, 5, 1)).failure(new AngleDetector(30, 5, 3)).fail(1).playSound(R.raw.dialturnshort).startSound(R.raw.safesuccess).display("front2").build());
        stageList.add(builder.solution(new AngleDetector( 70, 5, 1)).failure(new AngleDetector(80, 5, 3)).fail(2).playSound(R.raw.dialturnshort).startSound(R.raw.safesuccess).display("front3").build());

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
            int id = sp.load(this, this.currStage.sound(), 0);
            sp.play(id, 3,3, 0, 0, 1);
            configImage();
        }
        if(o == "CHANGED"){
            int id = sp.load(this, this.currStage.playSound(), 0);
            sp.play(id, 3,3, 0, 0, 1);
            configImage();
        }

    }
    private void configImage(){
        ImageView image = findViewById(R.id.img);
        image.setImageURI(currStage.display());
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setY(-200);
        TextView tv = findViewById(R.id.txtRot);
        tv.setZ(5);
        for (int i : DISPLAYVALUE){
            if(i == this.index){
                tv.setText(currStage.solutionValue());
                tv.setX(250);
                tv.setTextColor(Color.BLACK);
                tv.setY(700);
                return;
            }
            if(i != this.index){
                tv.setText("");
            }
        }
    }
    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        soundPool.play(sampleId,4,4,0,0,1);
    }
}
