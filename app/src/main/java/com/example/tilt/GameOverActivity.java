package com.example.tilt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        String time = getIntent().getStringExtra("TIME");
        String errors = getIntent().getStringExtra("ERRORS");
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvErrors = findViewById(R.id.tvSteps);

        tvTime.setText("TIME: " + time);
        tvTime.setText("ERRORS: " + errors);


    }
}
