package com.example.onetonline.presentation.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onetonlinev2.R;

public class RegisterForm extends AppCompatActivity {

    //Khai bao
    private Button btnBackRegisterForm, btnRegister;
    private TextView tvBackToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_form);
        //anh xa id
        btnBackRegisterForm = findViewById(R.id.btnBackRegisterForm);
        btnRegister = findViewById(R.id.btnRegister);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);
        //xử lý click
        btnBackRegisterForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterForm.this, WellComeScreen.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegister.setOnClickListener(v -> showOtpDialog());
        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterForm.this, LoginForm.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void showOtpDialog() {
        // Tạo Dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.confirm_otp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));// Xóa nen sau
        dialog.setCancelable(true); // Có thể tắt dialog khi nhấn bên ngoài


        // Xử lý nút Cancel
        Button btnCancel = dialog.findViewById(R.id.btnCancel_ConfirmOTP);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Hiển thị Dialog
        dialog.show();
    }
}