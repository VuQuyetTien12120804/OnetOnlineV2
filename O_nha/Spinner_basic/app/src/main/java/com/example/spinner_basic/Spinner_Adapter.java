package com.example.spinner_basic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Spinner_Adapter extends ArrayAdapter<SV> {
    Activity context;
    int layout;
    ArrayList<SV> list;

    public Spinner_Adapter(Activity context, int layout, ArrayList<SV> list) {
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

        SV sv = list.get(position);
        TextView txt_name = convertView.findViewById(R.id.txt_name);
        txt_name.setText(sv.getName());

        TextView txt_address = convertView.findViewById(R.id.txt_address);
        txt_address.setText(sv.getAddress());

        return convertView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
