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
import com.example.onetonline.presentation.model.Checker;
import com.example.onetonlinev2.R;
import com.mukeshsolanki.OtpView;

public class RegisterForm extends AppCompatActivity implements SignUpView{
    //Khai bao
    private Button btnBackRegisterForm, btnRegister;
    private EditText etUserName, etEmail, etPassword, etConfirmPassword;
    private TextView tvBackToLogin;
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!Checker.checkUserNameLen(etUserName.getText().toString())) {
                   showCustomToast("User Name must be more than 5 characters!");
               } else {
                   if (!Checker.checkEmail(etEmail.getText().toString())) {
                       showCustomToast("Invalid email address");
                   } else {
                       if (!Checker.checkPassLen(etPassword.getText().toString())) {
                           showCustomToast("Password must be more than 6 characters!");
                       } else {
                           if (!Checker.checkConfirmPassword(etPassword.getText().toString(), etConfirmPassword.getText().toString())) {
                               showCustomToast("Passwords do not match!");
                           } else {
                               showOtpDialog();
                               sign.handleSendOTP();
                           }
                       }
                   }
               }
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

    private void showCustomToast(String message){
        // Inflate layout cuar custom toast
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

    private void showOtpDialog() {
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

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = otpView.getText().toString();
                if(!Checker.checkOTPLen(otpView.getText().toString())){
                    showCustomToast("Enter your otp!");
                }
                else{
                    sign.handleSignUp();
                }
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign.handleReSendOTP();
            }
        });

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
    public void convertContext() {
        Intent intent = new Intent(RegisterForm.this, LoginForm.class);
        startActivity(intent);
        finish();
    }
}