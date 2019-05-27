package com.example.tilt;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class playMenuActivity extends AppCompatActivity {
    private String[] puzzleNames = {"Safe Cracker", "Illuminati"};
    private Integer[] puzzleImages = {R.drawable.front, R.drawable.unplugged};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_play_menu);
        ListView lvPuzzles = findViewById(R.id.lwPuzzles);
        PlayListAdapter adapter=new PlayListAdapter(this, puzzleNames, puzzleImages);
        lvPuzzles.setAdapter(adapter);
        lvPuzzles.setOnItemClickListener(listener());
    }


    private OnItemClickListener listener(){
        return new OnItemClickListener(){
            public void onItemClick(AdapterView<?> p, View v, int x, long id){
                TextView display = findViewById(R.id.txtPuzzle);
                String s = p.getItemAtPosition(x).toString();
                display.setText(s);

                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                        findViewById(R.id.btnPlay),
                        PropertyValuesHolder.ofFloat("scaleX", 1.4f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.4f));
                animator.setDuration(350);
                animator.setRepeatCount(ObjectAnimator.INFINITE);
                animator.setRepeatMode(ObjectAnimator.REVERSE);
                animator.start();
            }
        };
    }

    public void startGame(View v){
        TextView tv = findViewById(R.id.txtPuzzle);
        Log.d("txt", tv.getText().toString());

        if(tv.getText().toString().equals("Safe Cracker")){
            Intent i = new Intent(this, TutorialActivity.class);
            i.putExtra("STAGE", "Safe Cracker");
            startActivity(i);
        }else if(tv.getText().toString().equals("Illuminati")){
            Intent i = new Intent(this, TutorialActivity.class);
            i.putExtra("STAGE", "Illuminati");
            startActivity(i);
        }else{
            tv.setText("Please pick a puzzle");
        }
    }

    private List<String> getPuzzles(){
        List<String> ret = new ArrayList<>();
        for(String n : puzzleNames){
            ret.add(n);
        }
        return ret;
    }
}
