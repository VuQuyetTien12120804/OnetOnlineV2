package com.example.intent_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnSend;
    EditText edtSoa, edtSob;
    TextView txtvinkq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.btnGui);
        edtSoa = findViewById(R.id.edtSoa);
        edtSob = findViewById(R.id.edtSob);
        txtvinkq = findViewById(R.id.txtvinkq);

        Intent myintent = getIntent();
        int tong = myintent.getIntExtra("tong", 0);
        txtvinkq.setText("Kết quả là: " + tong);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Man_hinh_2.class);
                int soa = Integer.parseInt(edtSoa.getText().toString());
                int sob = Integer.parseInt(edtSob.getText().toString());
                intent.putExtra("soa", soa);
                intent.putExtra("sob", sob);
                startActivity(intent);
            }
        });

    }
}