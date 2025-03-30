package com.example.onetonline.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onetonline.business.User;
import com.example.onetonline.presentation.controller.LoginController;
import com.example.onetonline.presentation.model.Checker;
import com.example.onetonlinev2.R;

public class LoginForm extends AppCompatActivity implements LoginView{
    //Khai báo
    private Button btnBack, btnLogin;
    private EditText etLogin, etPassword;
    private LoginController loginController;

    public void initWidgets(){
        btnBack = findViewById(R.id.btnBackLoginForm);
        btnLogin = findViewById(R.id.btnLogin);
        etLogin = findViewById(R.id.etNameOrEmail);
        etPassword = findViewById(R.id.etPasswordLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_form);

        initWidgets();
        loginController = new LoginController(this, LoginForm.this);

        // Xử lý click
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginForm.this, WellComeScreen.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Checker.checkUserNameLen(etLogin.getText().toString())) {
                    Toast.makeText(LoginForm.this, "User Name must be more than 5 characters!", Toast.LENGTH_LONG).show();
                }
                else{
                    if (!Checker.checkPassLen(etPassword.getText().toString())) {
                        Toast.makeText(LoginForm.this, "Password must be more than 6 characters!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        loginController.handleLogin();
                    }
                }
            }
        });
    }

    @Override
    public String getUserName() {
        return etLogin.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(LoginForm.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void convertContext(User user) {
        Intent intent = new Intent(LoginForm.this, MenuGame.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}