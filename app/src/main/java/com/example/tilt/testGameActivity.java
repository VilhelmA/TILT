package com.example.tilt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class testGameActivity extends AppCompatActivity {
    private Game g;

    private List<Stage> stages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_game);
        this.stages = new ArrayList<>();
        this.createStages();
        this.g = new TestGame("Test", stages);
        this.g.start();
    }

    private void createStages(){
        // Create all the stages in here using the StageBuilder below and add them to the stages List.
        StageBuilder stageBuilder = new StageBuilder();
    }
}
