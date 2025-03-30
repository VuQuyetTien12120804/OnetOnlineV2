package com.example.onetonline.presentation.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onetonline.presentation.controller.SignUpController;
import com.example.onetonline.presentation.model.Checker;
import com.example.onetonlinev2.R;
import com.mukeshsolanki.OtpView;

public class RegisterForm extends AppCompatActivity implements SignUpView{
    //Khai bao
    private Button btnBackRegisterForm, btnRegister;
    private EditText etUserName, etEmail, etPassword, etConfirmPassword;
    private SignUpController sign;
    private String otp = "";

    public void initWidgets(){
        btnBackRegisterForm = findViewById(R.id.btnBackRegisterForm);
        btnRegister = findViewById(R.id.btnRegister);
        etUserName = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPasswordRegister);
        etConfirmPassword =findViewById(R.id.etConfirmPassword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_form);

        initWidgets();
        sign = new SignUpController(this, RegisterForm.this);

        //xử lý click
        btnBackRegisterForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterForm.this, WellComeScreen.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Checker.checkUserNameLen(etUserName.getText().toString())) {
                    Toast.makeText(RegisterForm.this, "User Name must be more than 5 characters!", Toast.LENGTH_LONG).show();
                }
                else{
                    if (!Checker.checkEmail(etEmail.getText().toString())) {
                        Toast.makeText(RegisterForm.this, "Invalid email address", Toast.LENGTH_LONG).show();
                    }
                    else{
                        if(!Checker.checkPassLen(etPassword.getText().toString())){
                            Toast.makeText(RegisterForm.this, "Password must be more than 6 characters!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if(!Checker.checkConfirmPassword(etPassword.getText().toString(), etConfirmPassword.getText().toString())){
                                Toast.makeText(RegisterForm.this, "Password and Confirm Password isn't match!", Toast.LENGTH_LONG).show();
                            }
                            else{
                                showOtpDialog();
                                sign.handleSendOTP();
                            }
                        }
                    }
                }
            }
        });
    }

    private void showOtpDialog() {
        // Tạo Dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.confirm_otp);
        dialog.setCancelable(true); // Có thể tắt dialog khi nhấn bên ngoài

        Button btnCancel;
        Button btnVerify;
        OtpView otpView;

        btnVerify = dialog.findViewById(R.id.btnVerify);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        otpView = dialog.findViewById(R.id.otp_view);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = otpView.getText().toString();
                if(!Checker.checkOTPLen(otpView.getText().toString())){
                    Toast.makeText(RegisterForm.this, "Enter your otp!", Toast.LENGTH_LONG).show();
                }
                else{
                    sign.handleSignUp();
                }
            }
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Hiển thị Dialog
        dialog.show();
    }

    @Override
    public String getUserName() {
        return etUserName.getText().toString();
    }

    @Override
    public String getEmail() {
        return etEmail.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return etConfirmPassword.getText().toString();
    }

    @Override
    public String getOTP() {
        return otp;
    }

    @Override
    public void ShowMessage(String message) {
        Toast.makeText(RegisterForm.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void convertContext() {
        Intent intent = new Intent(RegisterForm.this, LoginForm.class);
        startActivity(intent);
        finish();
    }
}