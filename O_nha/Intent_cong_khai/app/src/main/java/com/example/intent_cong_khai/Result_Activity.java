package com.example.intent_cong_khai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result_Activity extends AppCompatActivity {
    EditText edtnhan;
    Button btngoc, btnbinh;
    Intent myintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        edtnhan = findViewById(R.id.edtnhan);
        btngoc = findViewById(R.id.btngoc);
        btnbinh = findViewById(R.id.btnbinh);

        myintent = getIntent();
        int a = myintent.getIntExtra("so", 0);
        edtnhan.setText(a+"");
        btngoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myintent.putExtra("kq", a);
                setResult(33, myintent);
                finish();
            }
        });
        btnbinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myintent.putExtra("kq", a*a);
                setResult(34, myintent);
                finish();
            }
        });
    }
}