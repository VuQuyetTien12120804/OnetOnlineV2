package com.example.spinner_basic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String name[]={"Thu", "Lam", "Tung", "Anh"};
    String address[] = {"Hà Nội", "Hải Phòng", "Nam Định", "Bắc Giang"};

    ArrayList<SV> mylist;
    Spinner_Adapter myadapter;
    Spinner mysp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mysp = findViewById(R.id.spinner);

        mylist = new ArrayList<>();
        for (int i=0;i<name.length;i++){
            mylist.add(new SV(name[i], address[i]));
        }

        myadapter = new Spinner_Adapter(MainActivity.this, R.layout.layout_spinner, mylist);
        mysp.setAdapter(myadapter);

        mysp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SV selectedSV = mylist.get(position);
                Toast.makeText(MainActivity.this, "Chọn: " + selectedSV.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

}