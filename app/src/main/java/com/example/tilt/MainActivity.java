package com.example.tilt;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Dialog myDialog;
    // TODO: Package Management
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //myDialog = new Dialog(this);
        ImageView iv = (ImageView) findViewById(R.id.ivLogo);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                iv,
                PropertyValuesHolder.ofFloat("scaleX", 1.4f),
                PropertyValuesHolder.ofFloat("scaleY", 1.4f));
        animator.setDuration(350);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.REVERSE);

        ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(iv ,
                "rotation", 0f, 360f);
        imageViewObjectAnimator.setDuration(2000);
        imageViewObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        imageViewObjectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        // Starts animations
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator,imageViewObjectAnimator);

        animatorSet.start();

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
