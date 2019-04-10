package com.example.tilt;


import java.util.List;

public class TestGame extends Game {

    public TestGame(String name, List<Stage> stageList){
        super(name, stageList);
    }

    @Override
    public void start() {
        this.sk.start();
    }

    @Override
    public void end() {

    }



}
