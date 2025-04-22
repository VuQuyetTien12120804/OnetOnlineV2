package com.example.custom_gridview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int img[]={R.drawable.hinh1, R.drawable.hinh2, R.drawable.hinh3, R.drawable.hinh4, R.drawable.hinh5, R.drawable.hinh6,
            R.drawable.hinh7, R.drawable.hinh8};
    String name[]={"Gậy bẻ lò xo tập cơ", "Ấm siêu tốc Guckoo", "Tai nghe Liberty", "Sữa rửa mặt Vaseline", "Tất ngắn cho nam",
            "Quần giữ nhiệt nam", "Tai nghe bluetooth", "Bơ đậu phộng"};
    int gia[]={100000, 120000, 200000, 50000, 30000, 15000, 60000, 90000, 20000};

    ArrayList<Item> myitem;
    MyArrayAdapter myadapter;
    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv = findViewById(R.id.gv);
        myitem = new ArrayList<>();
        for (int i=0;i<name.length;i++){
            myitem.add(new Item(img[i], name[i], gia[i]));
        }
        myadapter = new MyArrayAdapter(MainActivity.this, R.layout.layout_item, myitem);
        gv.setAdapter(myadapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(MainActivity.this, Sub_Activity.class);
                myintent.putExtra("img", img[position]);
                myintent.putExtra("name", name[position]);
                myintent.putExtra("gia", gia[position]);
                startActivity(myintent);
            }
        });
    }


}