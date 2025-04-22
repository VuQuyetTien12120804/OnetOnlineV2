package com.example.intent_an;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;

public class Callphone_Activity extends AppCompatActivity {
    EditText edtso;
    ImageButton btncall;
    Button btnbackcall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callphone);

        edtso = findViewById(R.id.edtso);
        btncall = findViewById(R.id.btncall);
        btnbackcall = findViewById(R.id.btnbackcall);

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khai báo intent ẩn
                Intent callintent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+edtso.getText().toString()));
                //Yêu cầu sự đồng ý
                if (ActivityCompat.checkSelfPermission(Callphone_Activity.this, Manifest.permission.CALL_PHONE) !=PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Callphone_Activity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(callintent);
            }
        });
        btnbackcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}