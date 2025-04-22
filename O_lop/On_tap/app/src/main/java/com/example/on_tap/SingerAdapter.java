package com.example.on_tap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SingerAdapter extends ArrayAdapter<Singer> {
    Activity context;
    int layout;
    List<Singer> mylist;

    public SingerAdapter(Activity context, int layout, List<Singer> mylist) {
        super(context, layout, mylist);
        this.context = context;
        this.layout = layout;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(layout, null);

        Singer mysinger = mylist.get(position);

        String song = mysinger.getSong();
        TextView txtsong = convertView.findViewById(R.id.txtSong);
        txtsong.setText(song);

        float time = mysinger.getTime();
        TextView txttime = convertView.findViewById(R.id.txtTime);
        txttime.setText(time + "");

        String name = mysinger.getName();
        TextView txtname = convertView.findViewById(R.id.txtName);
        txtname.setText(name);

        return convertView;
    }
}
