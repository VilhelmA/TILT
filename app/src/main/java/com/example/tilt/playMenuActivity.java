package com.example.tilt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class playMenuActivity extends AppCompatActivity {
    private String[] puzzleNames = {"Spin to Win", "Dark room", "Funky Gravity"}; // Tillfällig ersättare för en framtida Factory.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_play_menu);
        ListView lvPuzzles = findViewById(R.id.lwPuzzles);
        List<String> puzzles = getPuzzles();
        String[] pNames = new String[puzzles.size()];
        pNames = puzzles.toArray(pNames);

        lvPuzzles.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pNames));
        lvPuzzles.setOnItemClickListener(listener());
    }


    private OnItemClickListener listener(){
        return new OnItemClickListener(){
            public void onItemClick(AdapterView<?> p, View v, int x, long id){
                TextView display = findViewById(R.id.txtPuzzle);
                String s = p.getItemAtPosition(x).toString();
                display.setText(s);
            }
        };
    }

    private List<String> getPuzzles(){
        List<String> ret = new ArrayList<>();
        for(String n : puzzleNames){
            ret.add(n);
        }
        return ret;
    }
}
