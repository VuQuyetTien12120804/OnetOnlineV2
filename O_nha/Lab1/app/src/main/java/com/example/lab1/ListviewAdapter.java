package com.example.lab1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListviewAdapter extends ArrayAdapter<String> {
    Activity context;
    int layout;
    ArrayList<String> list;

    public ListviewAdapter(Activity context, int layout, ArrayList<String> list) {
        super(context, layout, list);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinflater = context.getLayoutInflater();
        convertView = myinflater.inflate(layout, null);

        String a = list.get(position);
        TextView txtlistview = convertView.findViewById(R.id.txtlistview);
        txtlistview.setText(a);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
