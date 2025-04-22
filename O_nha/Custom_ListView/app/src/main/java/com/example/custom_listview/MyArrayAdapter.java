package com.example.custom_listview;

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

import java.util.ArrayList;
import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Phone> {
    Activity context;
    int layout;
    ArrayList<Phone> mylist;

    public MyArrayAdapter(int layout, Activity context, ArrayList<Phone> mylist) {
        super(context, layout, mylist);
        this.layout = layout;
        this.context = context;
        this.mylist = mylist;
    }

    // Gọi hàm getView để tiến hành sắp xếp dữ liệu
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Tạo đế chứa layout
        LayoutInflater myflactor = context.getLayoutInflater();
        // Đặt layout lên đế để tạo thành View
        convertView = myflactor.inflate(layout, null);
        // Lấy 1 phần tử trong mảng
        Phone myphone = mylist.get(position);

        ImageView imgphone = convertView.findViewById(R.id.imgphone);
        imgphone.setImageResource(myphone.getImage());

        TextView txtphone = convertView.findViewById(R.id.txtphone);
        txtphone.setText(myphone.getName());

        return convertView;
    }
}
