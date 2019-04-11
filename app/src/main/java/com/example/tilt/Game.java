package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;


public abstract class Game extends AppCompatActivity implements SensorEventListener  {
    protected ScoreKeeper sk;
    private int currstage;
    //private List<Stage> stageList;

    public Game(){
        this.sk = new ScoreKeeper();
    }

    public abstract void start();

    public abstract void end();

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
       // this.nextStage(this.stageList.get(currstage).solve(sensorEvent));
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do nothing, I have no clue what this does.
    }
    public void nextStage(int index){
        this.currstage = index;
    }
}
