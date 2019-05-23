package com.example.tilt;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TutorialActivity extends AppCompatActivity {
    private List<TextView> tvList = new ArrayList<>();
    private Button btn;
    private int clickCounter = 0;
    private String chosenPuzzle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenPuzzle = getIntent().getStringExtra("STAGE");
        setContentView(R.layout.activity_tutorial);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    public void onClick(View v){

        TextView tv1 = findViewById(R.id.tvOne);
        TextView tv2 = findViewById(R.id.tvTwo);
        TextView tv3 = findViewById(R.id.tvThree);
        TextView tv4 = findViewById(R.id.tvFour);
        tvList.add(tv1);
        tvList.add(tv2);
        tvList.add(tv3);
        tvList.add(tv4);


        btn = findViewById(R.id.btnGo);

        if(clickCounter < 4){
            tvList.get(clickCounter).setVisibility(TextView.VISIBLE);
        }

        if(clickCounter > 2){
            btn.setText("LET'S GO");

            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                    btn,
                    PropertyValuesHolder.ofFloat("scaleX", 1.4f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.4f));
            animator.setDuration(350);
            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.setRepeatMode(ObjectAnimator.REVERSE);
            animator.start();
        }

        if(clickCounter == 4){
            Intent i = null;
            if(chosenPuzzle.equals("Safe Cracker")){
                i = new Intent(this, SafeCrackerActivity.class);
            }else{
                i = new Intent(this, IlluminatiActivity.class);
            }

            startActivity(i);
        }

        clickCounter++;
    }
}
