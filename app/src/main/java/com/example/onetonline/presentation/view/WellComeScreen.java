package com.example.onetonline.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.onetonline.business.UserData;
import com.example.onetonline.presentation.BaseActivity;
import com.example.onetonline.presentation.controller.WellComeScreenController;
import com.example.onetonline.presentation.model.UserInf;
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
        userData = new UserData(this);
        UserInf userInf = userData.getData();
        if(userInf != null){
            Intent i = new Intent(WellComeScreen.this, MenuGame.class);
            i.putExtra("user", userInf);
            startActivity(i);
        }

        initWidgets();

        btnLogin.setOnClickListener(v -> wellComeScreenController.handleLogin(LoginForm.class));
        btnRegister.setOnClickListener(v -> wellComeScreenController.handleRegister(RegisterForm.class));
        btnGuest.setOnClickListener(v -> wellComeScreenController.handleGuest(MenuGame.class));
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
}