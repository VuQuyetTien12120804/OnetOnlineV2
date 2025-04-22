package com.example.btap1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton imageButton;
    EditText editTextSearch;
    ListView listView;
    private DBHelper mydb;

    @Override
    protected void onStart() {
        super.onStart();
        mydb.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mydb.close();
    }

    public void init(){
        imageButton = findViewById(R.id.imageButton);
        editTextSearch = findViewById(R.id.editTextSearch);
        listView = findViewById(R.id.listView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        init();
        mydb = new DBHelper(this);
        mydb.openDB();
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String singer = i.getStringExtra("singer");
        float time = i.getFloatExtra("time", 0);
        if(name != null && singer != null && time != 0) {
            mydb.Insert(name, singer, time);
            mydb.close();
        }
        ArrayList<String> arr = new ArrayList<String>();
        Cursor cursor = mydb.Display();
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            String n = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.Name));
            String s = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.Singer));
            String t = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.Time));
            arr.add(n + "\t" + t + "\n"  + "\t" + "\t" + s);
        }
        cursor.close();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(arrayAdapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
                finish();
            }
        });
    }
}