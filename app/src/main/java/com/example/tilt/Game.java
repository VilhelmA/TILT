package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class Game implements SensorEventListener {
    protected ScoreKeeper sk;
    private String name;
    private int currstage;
    private List<Stage> stageList;

    public Game(String name, List<Stage> stageList){
        this.stageList = stageList;
        this.name = name;
        this.sk = new ScoreKeeper();
    }

    public abstract void start();

    public abstract void end();

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        this.nextStage(this.stageList.get(currstage).solve(sensorEvent));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do nothing, I have no clue what this does.
    }
    public void nextStage(int index){
        this.currstage = index;
    }
}
