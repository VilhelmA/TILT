package com.example.tilt.stages;


import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tilt.GameOverActivity;
import com.example.tilt.R;
import com.example.tilt.detectors.AlwaysFail;
import com.example.tilt.detectors.AngleDetector;
import com.example.tilt.detectors.LightDetector;
import com.example.tilt.detectors.OrientationDetector;
import com.example.tilt.detectors.ProximityDetector;

import java.util.Observable;


public class SafeCrackerActivity extends Game implements SoundPool.OnLoadCompleteListener {
    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor magnoSensor;
    private Sensor rotSensor;
    private Sensor proxSensor;
    private Sensor lightSensor;
    private Sensor tempSensor;
    private int UPDATERATE = 10;
    private int updateCounter = 0;
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
        proxSensor= sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnoSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, rotSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_GAME);


        stageList.add(builder.solution(new OrientationDetector(OrientationDetector.VERTICALUPSIDEDOWN, 4)).fail(0).display("front").playSound(R.raw.dialturnshort).startSound(R.raw.silent).build());
        stageList.add(builder.solution(new OrientationDetector(OrientationDetector.VERTICAL, 3)).fail(0).display("belowfullsize").startSound(R.raw.swish).build());
        stageList.add(builder.solution(new AngleDetector( 201, 3, 1)).fail(1).playSound(R.raw.dialturnshorter).startSound(R.raw.swish).display("front").failure(new OrientationDetector(OrientationDetector.VERTICALUPSIDEDOWN, 4)).build());
        stageList.add(builder.solution(new AngleDetector( 43, 3, 1)).fail(1).playSound(R.raw.dialturnshorter).startSound(R.raw.safesuccess).display("front1").failure(new AngleDetector(220, 5, 3)).build());
        stageList.add(builder.solution(new AngleDetector( 140, 3, 1)).fail(2).playSound(R.raw.dialturnshorter).startSound(R.raw.safesuccess).display("front2").failure(new AngleDetector(33, 5, 3)).build());
        stageList.add(builder.solution(new OrientationDetector(OrientationDetector.RIGHT90DEG, 4)).fail(0).startSound(R.raw.safesuccess).display("front3").failure(new AlwaysFail()).build());
        stageList.add(builder.solution(new LightDetector(2000)).display("dark").startSound(R.raw.open).playSound(R.raw.silent).failure(new AlwaysFail()).build());
        stageList.add(builder.solution(new ProximityDetector(4)).display("win").startSound(R.raw.silent).playSound(R.raw.silent).failure(new AlwaysFail()).build());
        
        for(Stage s : stageList){
            s.addObserver(this);
        }
        this.start();
    }

    @Override
    public void start() {
        this.sk.start();
    }

    private void closeSensors(){
        sensorManager.unregisterListener(this);
    }
    @Override
    public void end() {
        this.sk.end();
        closeSensors();
        Intent i = new Intent(this, GameOverActivity.class);
        long seconds = this.sk.end().getSeconds();
        int minutes = (int) seconds / 60;
        seconds = seconds - minutes * 60;
        i.putExtra("TIME", minutes + ":" + seconds);
        i.putExtra("STAGE", "SAFE CRACKER");
        startActivity(i);
    }

    @Override
    public void update(Observable observable, Object o) {

        if(o == "CREATED"){ // Play only on create
            //sp.release();
            if(currStage.sound() != 0){
                int id = sp.load(this, this.currStage.sound(), 0);
                sp.play(id, 3,3, 0, 0, 1);
            }
            configImage();

        }
        if(o == "CHANGED"){
            //sp.release();
            updateCounter++;
            if(updateCounter == UPDATERATE){
                if(currStage.playSound() != 0) {
                    int id = sp.load(this, this.currStage.playSound(), 0);
                    sp.play(id, 3, 3, 0, 0, 1);
                    updateCounter = 0;
                }
            }
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
                tv.setX(375);
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




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            sensorManager.unregisterListener(this);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
