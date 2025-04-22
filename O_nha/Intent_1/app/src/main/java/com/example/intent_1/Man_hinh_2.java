package com.example.intent_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Man_hinh_2 extends AppCompatActivity {
    Button btnBack;
    TextView txtvkq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh2);

        btnBack = findViewById(R.id.btnBack);
        txtvkq = findViewById(R.id.txtvkq);

        Intent myintent = getIntent();
        int a = myintent.getIntExtra("soa", 0);
        int b = myintent.getIntExtra("sob", 0);
        int tong = a + b;

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Man_hinh_2.this, MainActivity.class);
                intent.putExtra("tong", tong);
                startActivity(intent);
            }
        });
    }
}