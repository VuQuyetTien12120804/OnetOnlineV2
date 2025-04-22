package com.example.on_tap;

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

public class ManHinh2 extends AppCompatActivity {
    EditText edtName, edtSong, edtTime;
    Button btnAddSong, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh2);
        edtName = findViewById(R.id.editName);
        edtSong = findViewById(R.id.editSong);
        edtTime = findViewById(R.id.editTime);
        btnAddSong = findViewById(R.id.btnAddSong);
        btnBack = findViewById(R.id.btnBack);

        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String song = edtSong.getText().toString();
                float time = Float.parseFloat(edtTime.getText().toString());

                Intent intent = new Intent(ManHinh2.this, MainActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("song", song);
                intent.putExtra("time", time);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}