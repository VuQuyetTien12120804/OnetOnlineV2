package com.example.demologin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManHinhGame extends AppCompatActivity {
    TextView txtdata;
    Button btnSignOut;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_game);

        btnSignOut = findViewById(R.id.btnSignOut);
        txtdata = findViewById(R.id.txtdata);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        txtdata.setText(user.getEmail() + " - "  + user.getPassword() + " -" + user.getLevel());

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setDeleted(1);
                Intent intent1 = new Intent(ManHinhGame.this, MainActivity.class);
                intent1.putExtra("userSignOut", user);
                startActivity(intent1);
            }
        });

    }
}