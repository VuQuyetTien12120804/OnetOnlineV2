package com.example.btap1;

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

public class MainActivity2 extends AppCompatActivity {
    EditText etName, etSinger, etTime;
    Button buttonBack, buttonAdd;

    public void init(){
        etName = findViewById(R.id.editTextName);
        etSinger = findViewById(R.id.editTextSinger);
        etTime = findViewById(R.id.editTextTime);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonBack = findViewById(R.id.buttonBack);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        init();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = etName.getText().toString();
                String Singer = etSinger.getText().toString();
                float Time = Float.parseFloat(etTime.getText().toString());
                Intent i = new Intent(MainActivity2.this, MainActivity.class);
                i.putExtra("name", Name);
                i.putExtra("singer", Singer);
                i.putExtra("time", Time);
                startActivity(i);
                finish();
            }
        });
    }
}