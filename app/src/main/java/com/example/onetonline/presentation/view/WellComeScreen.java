package com.example.onetonline.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onetonline.business.UserData;
import com.example.onetonline.presentation.controller.WellComeScreenController;
import com.example.onetonline.presentation.model.UserInf;
import com.example.onetonlinev2.R;

public class WellComeScreen extends AppCompatActivity {
    /**
     *
     */
    private UserData userData;
    private WellComeScreenController wellComeScreenController;
    /**
     *
     */
    private Button btnLogin, btnRegister, btnGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come_screen);
        wellComeScreenController = new WellComeScreenController(this);

        wellComeScreenController.handleHasRecord(MenuGameForm.class);

        initWidgets();

        btnLogin.setOnClickListener(v -> wellComeScreenController.handleLogin(LoginForm.class));
        btnRegister.setOnClickListener(v -> wellComeScreenController.handleRegister(SignUpForm.class));
        btnGuest.setOnClickListener(v -> wellComeScreenController.handleGuest(MenuGameForm.class));
    }

    public void initWidgets(){
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnGuest = findViewById(R.id.btnGuest);
    }

    public void navigateTo(Class<?> avtivityClass) {
        Intent intent = new Intent(this, avtivityClass);
        startActivity(intent);
        finish();
    }
}