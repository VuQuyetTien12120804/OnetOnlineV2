package com.example.onetonline.presentation.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.onetonline.business.UserData;
import com.example.onetonline.presentation.BaseActivity;
import com.example.onetonline.presentation.controller.WellComeScreenController;
import com.example.onetonlinev2.R;

public class WellComeScreen extends BaseActivity implements WellComeScreenView {
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

    @Override
    public void navigateTo(Class<?> avtivityClass) {
        Intent intent = new Intent(this, avtivityClass);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to exit the app?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }

}