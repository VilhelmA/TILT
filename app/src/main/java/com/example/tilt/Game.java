package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public abstract class Game extends AppCompatActivity implements SensorEventListener  {
    protected ScoreKeeper sk = new ScoreKeeper();
    protected int currStage;
    protected List<Stage> stageList = new ArrayList<>();

    public abstract void start();

    public abstract void end();

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Log.d("CURRENT STAGE", currStage+"");
        int t = currStage;
        int r = this.stageList.get(currStage).solve(sensorEvent);
        Log.d("RET", r+"");
        this.nextStage(r);
        if(t != currStage){
            this.stageList.get(currStage).onCreate(); // If the Stage has changed, reconfigure.
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do nothing, I have no clue what this does.
    }

    public void nextStage(int index){
        this.currStage = index;
    }
}
