package com.example.intent_an;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Sendsms_Activity extends AppCompatActivity {
    EditText edtsms;
    ImageButton btnsms;
    Button btnbacksms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendsms);
        edtsms = findViewById(R.id.edtsosms);
        btnsms = findViewById(R.id.btnsms);
        btnbacksms = findViewById(R.id.btnbacksms);

        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsintent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+edtsms.getText().toString()));
                startActivity(smsintent);
            }
        });
        btnbacksms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}