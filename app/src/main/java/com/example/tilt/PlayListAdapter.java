package com.example.tilt;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final Integer[] imgid;

    public PlayListAdapter(Activity context, String[] maintitle, Integer[] imgid) {
        super(context, R.layout.adapter_layout, maintitle);
        this.context=context;
        this.maintitle=maintitle;
        this.imgid=imgid;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.adapter_layout, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        titleText.setText(maintitle[position]);
        imageView.setImageResource(imgid[position]);

        return rowView;

    };
}