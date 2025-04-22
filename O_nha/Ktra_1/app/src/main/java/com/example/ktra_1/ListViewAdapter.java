package com.example.ktra_1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText$InspectionCompanion;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<String> {
    Activity context;
    int layout;
    ArrayList<String> list;

    public ListViewAdapter(Activity context, int layout, ArrayList<String> list) {
        super(context, layout, list);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinfloater = context.getLayoutInflater();
        convertView = myinfloater.inflate(layout, null);

        String a = list.get(position);
        TextView txtlayout = convertView.findViewById(R.id.txtlayout);
        txtlayout.setText(a);

        return convertView;
    }
}
