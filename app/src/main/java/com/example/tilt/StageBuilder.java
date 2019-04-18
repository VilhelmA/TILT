package com.example.tilt;

import android.net.Uri;
import java.util.ArrayList;
import java.util.List;


public class StageBuilder {
    private List<Detector> failures = new ArrayList<>();
    private Detector solution;
    private Uri sound;
    private Uri display;
    private int fail;

    public StageBuilder sound(String filename){
        this.sound = Uri.parse("android.resource://com.example.tilt/raw/"+filename);
        return this;
    }

    public StageBuilder display(Uri display){
        this.display = display;
        return this;
    }

    public StageBuilder solution(Detector e){
        this.solution = e;
        return this;
    }

    public StageBuilder failure(Detector e) {
        this.failures.add(e);
        return this;
    }

    public StageBuilder failure(List<Detector> failures){
        failures.addAll(failures);
        return this;
    }

    public StageBuilder fail(int fail){
        this.fail = fail;
        return this;
    }


    public Stage build(){
        return new Stage(this.sound, this.display, this.solution, this.failures, this.fail);
    }


}
