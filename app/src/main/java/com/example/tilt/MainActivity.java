package com.example.tilt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // TODO: Package Management
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playOnClick(View v){
        Intent i = new Intent(this, playMenuActivity.class);
        startActivity(i);
    }
    public void highScoreOnClick(View v){
        Intent i = new Intent(this, HighScoreActivity.class);
        startActivity(i);
    }
}
