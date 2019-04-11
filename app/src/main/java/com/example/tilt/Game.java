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
    protected Stage currStage;
    private int index;
    protected List<Stage> stageList = new ArrayList<>();

    public abstract void start();

    public abstract void end();

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(currStage == null){
            currStage = stageList.get(index);
        }
        int r = currStage.solve(sensorEvent);
        this.nextStage(r);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do nothing, I have no clue what this does.
    }

    /**
     *
     * @param i
     */
    public void nextStage(int i){
        if(i != 0) {
            Log.d("NEXTSTAGE", i + "");
            this.index = this.index + i;
            if(this.index <= stageList.size()-1){
                currStage = stageList.get(this.index);
                currStage.onCreate();
            }
        }
    }
}
