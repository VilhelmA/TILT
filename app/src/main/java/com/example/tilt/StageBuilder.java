package com.example.tilt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.util.ArrayList;
import java.util.List;


public class StageBuilder {
    private List<SensorEvent> failures = new ArrayList<>();
    private SensorEvent solution;
    private String sound;
    private String display;
    private int fail;
    private List<Sensor> sensors = new ArrayList<>();

    public StageBuilder sound(String sound){
        this.sound = sound;
        return this;
    }

    public StageBuilder display(String display){
        this.display = display;
        return this;
    }

    public StageBuilder solution(SensorEvent e){
        this.solution = solution;
        return this;
    }

    public StageBuilder failure(SensorEvent e) {
        this.failures.add(e);
        return this;
    }

    public StageBuilder failure(List<SensorEvent> failures){
        failures.addAll(failures);
        return this;
    }

    public StageBuilder fail(int fail){
        this.fail = fail;
        return this;
    }

    public StageBuilder sensor(Sensor s){
        this.sensors.add(s);
        return this;
    }

    public StageBuilder sensor(List<Sensor> sensors){
        this.sensors.addAll(sensors);
        return this;
    }

    public Stage build(){
        return new Stage(this.sound, this.display, this.solution, this.failures, this.sensors, this.fail);
    }


}
