package com.example.custom_gridview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Item> {
    Activity context;
    int Idlayout;
    ArrayList<Item> mylist;

    public MyArrayAdapter(Activity context, int idlayout, ArrayList<Item> mylist) {
        super(context, idlayout, mylist);
        this.context = context;
        Idlayout = idlayout;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myflactor = context.getLayoutInflater();
        convertView = myflactor.inflate(Idlayout, null);
        Item myitem = mylist.get(position);

        ImageView imgitem = convertView.findViewById(R.id.imgitem);
        imgitem.setImageResource(myitem.getImage());
        TextView txttensp = convertView.findViewById(R.id.txttensp);
        txttensp.setText(myitem.getName());
        TextView txtgiasp = convertView.findViewById(R.id.txtgiasp);
        txtgiasp.setText(myitem.getName());

        return convertView;
    }
}
