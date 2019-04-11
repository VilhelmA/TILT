package com.example.tilt;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class testGameActivity extends Game {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_game);
        this.start();
    }

    public void end(View v){
        this.end();
        TextView tv = findViewById(R.id.textView);
        tv.setText(this.sk.end().getSeconds()+ " ");
        this.start();
    }
    @Override
    public void start() {
        this.sk.start();
    }

    @Override
    public void end() {
        this.sk.end();
    }
}
