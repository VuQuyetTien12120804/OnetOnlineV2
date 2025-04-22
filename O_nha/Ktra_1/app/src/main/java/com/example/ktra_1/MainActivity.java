package com.example.ktra_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtname;
    ListView lv;
    ListViewAdapter lvadapter;
    ArrayList<String> lvlist;
    CheckBox chkgiamgia;
    Button btngui;
    String tenhang, name;
    int giamgia, index;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtname = findViewById(R.id.edtname);
        chkgiamgia = findViewById(R.id.chkgiamgia);
        btngui = findViewById(R.id.btngui);
        txt = findViewById(R.id.txt);

        lv = findViewById(R.id.lv);
        lvlist = new ArrayList<>();
        lvlist.add("Do choi");
        lvlist.add("May giat");
        lvlist.add("TV");
        lvlist.add("Tu lanh");
        lvadapter = new ListViewAdapter(MainActivity.this, R.layout.layoutlistview, lvlist);
        lv.setAdapter(lvadapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenhang = lvlist.get(position);
                index = position;
                Toast.makeText(MainActivity.this, "Ban da chon: " + tenhang, Toast.LENGTH_SHORT).show();
            }
        });

        btngui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edtname.getText().toString();
                if (chkgiamgia.isChecked()){
                    giamgia = 1;
                }else giamgia = 0;
                Intent myintent = new Intent(MainActivity.this, ManHinh2.class);
                myintent.putExtra("name", name);
                myintent.putExtra("mh", tenhang);
                myintent.putExtra("check", giamgia);
                myintent.putExtra("index", index);
                startActivity(myintent);
            }
        });
    }
}