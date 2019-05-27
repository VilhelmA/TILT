package com.example.tilt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        TextView tvScore = findViewById(R.id.tvScore);
        SharedPreferences prefs = this.getSharedPreferences("highscores", Context.MODE_PRIVATE);
        String score = prefs.getString("SAFE CRACKER", "Level not completed");
        String illum = prefs.getString("Illuminati", "Level not completed");

        tvScore.setText("Safe Cracker: " + score + "\nIlluminati: " + illum);
    }

    public void returnToMenu(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
