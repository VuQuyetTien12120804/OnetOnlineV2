package com.example.custom_gridview;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Sub_Activity extends AppCompatActivity {
    TextView txtsubtensp, txtsubgiasp;
    ImageView imgsubsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        txtsubtensp = findViewById(R.id.txtsubtensp);
        txtsubgiasp = findViewById(R.id.txtsubgiasp);
        imgsubsp = findViewById(R.id.imgsubsp);

        Intent myintent = getIntent();
        String ten = myintent.getStringExtra("name");
        int gia = myintent.getIntExtra("gia", 0);
        int anh = myintent.getIntExtra("img", 0);

        txtsubtensp.setText(ten);
        txtsubgiasp.setText("Giá: " + gia);
        imgsubsp.setImageResource(anh);
    }
}