package com.example.tilt.stages;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.example.tilt.GameOverActivity;
import com.example.tilt.R;
import com.example.tilt.Waiter;
import com.example.tilt.detectors.ChargeDetector;
import com.example.tilt.detectors.OrientationDetector;
import com.example.tilt.detectors.ProximityDetector;

import java.util.Observable;

public class IlluminatiActivity extends Game implements SoundPool.OnLoadCompleteListener {
    private SensorManager sensorManager;
    private SoundPool sp;
    private Sensor accelSensor;
    private Sensor proxSensor;
    private static final int[] VIBRATE = {1};
    private Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illuminati);
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        this.sp.setOnLoadCompleteListener(this);
        this.vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_GAME);
        Context c = getApplicationContext();
        stageList.add(builder.solution(new ChargeDetector(c)).fail(0).display("shade").build());
        stageList.add(builder.solution(new Waiter(10000)).fail(0).display("plugged").startSound(R.raw.lightbulb).build());
        stageList.add(builder.solution(new ProximityDetector(1)).fail(0).display("plugged").startSound(R.raw.ring).build());
        stageList.add(builder.solution(new OrientationDetector(OrientationDetector.UPSIDEDOWN, 1)).fail(0).display("phone").startSound(R.raw.hello).build());
        
        for(Stage s : stageList){
            s.addObserver(this);
        }

        this.start();
    }

    @Override
    protected void start() {
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
        i.putExtra("STAGE", "Illuminati");
        startActivity(i);
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o == "CREATED"){
            if(currStage.sound() != 0){
                int id = sp.load(this, this.currStage.sound(), 0);
                sp.play(id, 3,3, 0, 0, 1);
            }
            configImage();
        }

        if(o == "CHANGED"){
                if(currStage.playSound() != 0) {
                    int id = sp.load(this, this.currStage.playSound(), 0);
                    sp.play(id, 3, 3, 0, 0, 1);
                }
            configImage();
        }

    }

    private void configImage(){
        ImageView image = findViewById(R.id.img);
        image.setImageURI(currStage.display());
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setY(-200);
        for(int i : VIBRATE){
            if(i == this.index){
                for(int k = 0; k < 90; k++){
                    if(k%3 == 0)
                        vib.vibrate(VibrationEffect.createOneShot(500, 200));
                }
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
