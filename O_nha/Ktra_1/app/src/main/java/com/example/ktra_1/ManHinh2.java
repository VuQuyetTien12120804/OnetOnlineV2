package com.example.ktra_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManHinh2 extends AppCompatActivity {
    EditText edtkh, edtmh, edtgiam;
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh2);
        edtkh = findViewById(R.id.edtkhachhang);
        edtmh = findViewById(R.id.edtmathang);
        edtgiam = findViewById(R.id.edtgiam);
        btnback = findViewById(R.id.btnback);

        Intent intent = getIntent();
        String a = intent.getStringExtra("name");
        String b = intent.getStringExtra("mh");
        int c = intent.getIntExtra("check", 0);
        int listtien[] = {100,200,300,400};

        edtkh.setText(a);
        edtmh.setText(b);
        if (c==1){
            edtgiam.setText("Co");
        }else edtgiam.setText("Khong");

        int index = intent.getIntExtra("index", 0);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c==1) Toast.makeText(ManHinh2.this, b + " giam gia con: " + listtien[index], Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}