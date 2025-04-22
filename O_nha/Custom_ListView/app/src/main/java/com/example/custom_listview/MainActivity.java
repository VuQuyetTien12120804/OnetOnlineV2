package com.example.custom_listview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int image[] = {R.drawable.ip, R.drawable.bphone, R.drawable.samsung};
    String name[] = {"Điện thoại Iphone", "Điện thoại Bphone", "Điện thoại Samsung"};

    //Khai báo ListView
    ArrayList<Phone> mylist;
    MyArrayAdapter myadapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        for (int i=0;i<name.length;i++){
            mylist.add(new Phone(image[i], name[i]));
        }
        myadapter = new MyArrayAdapter(R.layout.layout_item, MainActivity.this, mylist);
        lv.setAdapter(myadapter);

        //Xử lý sự kiện click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(MainActivity.this, Sub_Activity.class);
                myintent.putExtra("name", name[position]);
                startActivity(myintent);
            }
        });
    }
}