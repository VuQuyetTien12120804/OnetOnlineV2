package com.example.onetonline.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.onetonlinev2.R;

public class LoginForm extends AppCompatActivity {

    //Khai báo
    private Button btnBackLoginForm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.id_form_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ánh xa id
        btnBackLoginForm = findViewById(R.id.btnBackLoginForm);

        // Xử lý click
        btnBackLoginForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginForm.this, WellComeScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}