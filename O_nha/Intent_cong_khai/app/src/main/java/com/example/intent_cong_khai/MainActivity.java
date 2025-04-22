package com.example.intent_cong_khai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtso, edtkq;
    Button btngui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtso = findViewById(R.id.edtso);
        edtkq = findViewById(R.id.edtkq);
        btngui = findViewById(R.id.btngui);

        btngui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myinent = new Intent(MainActivity.this, Result_Activity.class);
                int so = Integer.parseInt(edtso.getText().toString());
                myinent.putExtra("so", so);
                startActivityForResult(myinent, 99);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 & resultCode == 33){
            int kq = data.getIntExtra("kq", 0);
            edtkq.setText("Giá trị gốc là: " + kq);
        }
        if (requestCode == 99 & resultCode == 34){
            int kq = data.getIntExtra("kq", 0);
            edtkq.setText("Giá trị bình phương là: " + kq);
        }

    }
}