package com.example.tilt;

import android.net.Uri;



public class StageBuilder {
    private Detector failure;
    private Detector solution;
    private Uri startSound;
    private Uri playSound;
    private Uri display;
    private int startID;
    private int playID;
    private int fail;


    public StageBuilder startSound(int id){
        this.startID = id;
        return this;
    }

    public StageBuilder playSound(int id){
        this.playID = id;
        return this;
    }
    public StageBuilder display(String filename){
        this.display = Uri.parse("android.resource://com.example.tilt/drawable/"+filename);
        return this;
    }

    public StageBuilder solution(Detector e){
        this.solution = e;
        return this;
    }

    public StageBuilder failure(Detector e) {
        this.failure = e;
        return this;
    }



    public StageBuilder fail(int fail){
        this.fail = fail;
        return this;
    }


    public Stage build(){
        return new Stage(this.startSound, this.playSound, this.display, this.solution, this.failure, this.fail, this.startID, this.playID);
    }


}
