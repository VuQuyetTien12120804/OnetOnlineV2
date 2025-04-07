package com.example.onetonline.presentation.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onetonline.presentation.controller.SignUpController;
import com.example.onetonlinev2.R;
import com.mukeshsolanki.OtpView;

public class SignUpForm extends AppCompatActivity implements SignUpView{
    private Button btnBackRegisterForm, btnRegister;
    private EditText etUserName, etEmail, etPassword, etConfirmPassword;
    private TextView tvBackToLogin;
    private SignUpController sign;
    private String otp = "";

    public void initWidgets(){
        btnBackRegisterForm = findViewById(R.id.btnBackRegisterForm);
        btnRegister = findViewById(R.id.btnRegister);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);
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
        sign = new SignUpController(this, SignUpForm.this);

        btnBackRegisterForm.setOnClickListener(v -> sign.handleBackToHome(WellComeScreen.class));
        btnRegister.setOnClickListener(v -> sign.handleSendOTP());
        tvBackToLogin.setOnClickListener(v -> sign.handleBackToLogin(LoginForm.class));
    }

    public void showCustomToast(String message){
        // Inflate layout cua custom toast
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView toastMessage = layout.findViewById(R.id.toast_message);
        toastMessage.setText(message);

        //tao toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public void showOtpDialog() {
        // Tạo Dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.confirm_otp);
        dialog.setCancelable(true); // Có thể tắt dialog khi nhấn bên ngoài
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));// Xóa nen sau

        Button btnCancel;
        Button btnResend;
        Button btnVerify;
        OtpView otpView;

        btnVerify = dialog.findViewById(R.id.btnVerify_ConfirmOTP);
        btnResend = dialog.findViewById(R.id.btnResend_ConfirmOTP);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        otpView = dialog.findViewById(R.id.otp_view);

        btnVerify.setOnClickListener(v -> {
            otp = otpView.getText().toString();
            sign.handleSignUp();
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnResend.setOnClickListener(v -> sign.handleReSendOTP());

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
    public void showMessage(String message) {
        showCustomToast(message);
    }

    @Override
    public void navigateTo(Class<?> avtivityClass) {
        Intent intent = new Intent(SignUpForm.this, avtivityClass);
        startActivity(intent);
        finish();
    }
}