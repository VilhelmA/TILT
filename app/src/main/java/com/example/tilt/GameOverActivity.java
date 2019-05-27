package com.example.tilt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity{

    private SoundPool sp = new SoundPool(2,AudioManager.STREAM_MUSIC, 0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        String time = getIntent().getStringExtra("TIME");
        TextView tvTime = findViewById(R.id.tvTime);
        tvTime.setText("TIME: " + time);
        saveScore(getIntent().getStringExtra("STAGE"), time);
        playSound();
    }
    private void playSound(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.congrats);
        mp.start();
    }

    private void saveScore(String stage, String time){
        SharedPreferences sharedPreferences = this.getSharedPreferences("highscores",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(stage, time);
        editor.commit();
    }

    public void returnToMenu(View v){
       Intent i = new Intent(this, MainActivity.class);
       startActivity(i);
    }
}
