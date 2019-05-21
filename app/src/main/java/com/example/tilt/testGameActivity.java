package com.example.tilt;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Observable;


public class testGameActivity extends Game implements SoundPool.OnLoadCompleteListener {
    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor magnoSensor;
    private Sensor rotSensor;
    private Sensor proxSensor;
    private Sensor lightSensor;
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

        StageBuilder builder = new StageBuilder();
        stageList.add(builder.solution(new OrientationDetector(OrientationDetector.VERTICALUPSIDEDOWN, 3)).fail(0).display("front").playSound(R.raw.dialturnshort).build());
        stageList.add(builder.solution(new OrientationDetector(OrientationDetector.VERTICAL, 3)).fail(0).display("belowfullsize").build());
        stageList.add(builder.solution(new AngleDetector( 90, 3, 1)).fail(0).playSound(R.raw.dialturnshorter).display("front").build());
        stageList.add(builder.solution(new AngleDetector( 40, 3, 1)).fail(1).playSound(R.raw.dialturnshorter).startSound(R.raw.safesuccess).display("front1").failure(new AngleDetector(120, 5, 3)).build());
        stageList.add(builder.solution(new AngleDetector( 70, 3, 1)).fail(2).playSound(R.raw.dialturnshorter).startSound(R.raw.safesuccess).display("front2").failure(new AngleDetector(20, 5, 3)).build());
        stageList.add(builder.solution(new ProximityDetector(4)).fail(3).startSound(R.raw.safesuccess).display("front3").failure(new AngleDetector(400, 5, 3)).build());
        stageList.add(builder.solution(new LightDetector(2000, 200)).display("dark").build());
        stageList.add(builder.solution(new ProximityDetector(4)).display("win").build());
        //.failure(new AngleDetector(80, 5, 3))
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
    public void end() { this.sk.end();
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
