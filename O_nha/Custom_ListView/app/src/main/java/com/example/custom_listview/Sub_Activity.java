package com.example.custom_listview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Sub_Activity extends AppCompatActivity {
    TextView txtpsubhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        txtpsubhone = findViewById(R.id.txtsubphone);

        Intent myintent = getIntent();
        String a = myintent.getStringExtra("name");
        txtpsubhone.setText(a);
    }
}