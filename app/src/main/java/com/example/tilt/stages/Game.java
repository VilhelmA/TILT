package com.example.tilt.stages;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;

import com.example.tilt.ScoreKeeper;
import com.example.tilt.stages.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;


public abstract class Game extends AppCompatActivity implements SensorEventListener, Observer {
    protected ScoreKeeper sk = new ScoreKeeper();
    protected Stage currStage;
    protected int index;
    protected List<Stage> stageList = new ArrayList<>();
    protected StageBuilder builder = new StageBuilder();

    protected abstract void start();

    /**
     * The end method is supposed to run after the game has finished.
     */
    protected abstract void end();

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(currStage == null){
            currStage = stageList.get(index);
            currStage.onCreate();
        }
        int r = currStage.solve(sensorEvent);
        this.nextStage(r);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do nothing, I have no clue what this does.
    }


    public void nextStage(int i){
        if(i != 0){
            this.index = this.index + i;
            if(this.index <= stageList.size()-1){
                currStage = stageList.get(this.index);
                currStage.onCreate();
            }else{
                this.end(); // Run when out of stages
            }
        }

    }
}
