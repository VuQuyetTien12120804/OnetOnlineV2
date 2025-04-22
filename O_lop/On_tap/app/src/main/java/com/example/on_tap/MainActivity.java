package com.example.on_tap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    SingerAdapter myadapter;
    ArrayList<Singer> mylist;
    Button btnAdd;
    MyDB mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        btnAdd = findViewById(R.id.btnAdd);

        mylist = new ArrayList<>();
        myadapter = new SingerAdapter(this, R.layout.layout_singer, mylist);
        lv.setAdapter(myadapter);

        mydatabase = new MyDB(this);
        mydatabase.openDB();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String song = intent.getStringExtra("song");
        String time = String.valueOf(intent.getFloatExtra("time", 0));
        if (name!=null && song!=null && time!=null){
            mydatabase.Insert(song, name, time);
        }

        Cursor c = mydatabase.DisplayAll();
        c.moveToFirst();
        Singer singer;
        while(c.isAfterLast() == false){
            singer = new Singer(c.getString(1) , c.getString(2),c.getFloat(3));
            c.moveToNext();
            mylist.add(singer);
        }
        c.close();
        myadapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManHinh2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mydatabase.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mydatabase.closeDB();
    }
}