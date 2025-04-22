package com.example.o_intent_chuyen_doi_tuong;

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

public class MainActivity extends AppCompatActivity {
    EditText edtnamsinh, edthoten;
    Button btnsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edthoten = findViewById(R.id.edtHoten);
        edtnamsinh = findViewById(R.id.edtNamsinh);
        btnsend = findViewById(R.id.btnsend);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ht = edthoten.getText().toString();
                int ns = Integer.parseInt(edtnamsinh.getText().toString());

                Student sv = new Student(ht, ns);

                Bundle bundle = new Bundle();
 //               bundle.putSerializable("sinhvien", sv);
                bundle.putString("hoten", ht);
                bundle.putInt("namsinh", ns);

                Intent intent = new Intent(MainActivity.this, Manhinh2.class);
                intent.putExtra("bundle", bundle);

//               intent.putExtra("sinhvien", sv);

                startActivity(intent);
            }
        });

    }
}