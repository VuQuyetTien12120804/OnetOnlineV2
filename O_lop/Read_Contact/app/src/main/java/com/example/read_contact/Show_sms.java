package com.example.read_contact;

import android.database.Cursor;
import android.net.Uri;
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
import java.util.List;

public class Show_sms extends AppCompatActivity {
    ListView lvsms;
    Button btnback1;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sms);
        btnback1 = findViewById(R.id.btnback1);
        lvsms = findViewById(R.id.lvsms);
        btnback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        readSMS();
    }

    private void readSMS() {
        Uri uri = Uri.parse("content://sms");
        Cursor c2 = getContentResolver().query(uri, null, null, null, null);
        //arrayList.clear();
        arrayList = new ArrayList<>();
        c2.moveToFirst();
        while(!c2.isAfterLast()){
            int index_phonenumber = c2.getColumnIndex("address");
            int index_date = c2.getColumnIndex("date");
            int index_body = c2.getColumnIndex("body");
            String phone_number = c2.getString(index_phonenumber);
            String date = c2.getString(index_date);
            String body = c2.getString(index_body);
            arrayList.add(phone_number + "\n" + date + "\n" +body);
            c2.moveToNext();
        }
        c2.close();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        lvsms.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}