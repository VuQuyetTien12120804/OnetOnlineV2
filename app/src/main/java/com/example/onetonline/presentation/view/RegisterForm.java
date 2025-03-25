package com.example.onetonline.presentation.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onetonlinev2.R;

public class RegisterForm extends AppCompatActivity {

    //Khai bao
    private Button btnBackRegisterForm, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_form);
        //anh xa id
        btnBackRegisterForm = findViewById(R.id.btnBackRegisterForm);
        btnRegister = findViewById(R.id.btnRegister);
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
    }
    private void showOtpDialog() {
        // Tạo Dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.confirm_otp);
        dialog.setCancelable(true); // Có thể tắt dialog khi nhấn bên ngoài

        // Tham chiếu các View trong Dialog
        EditText otp1 = dialog.findViewById(R.id.otp1);
        EditText otp2 = dialog.findViewById(R.id.otp2);
        EditText otp3 = dialog.findViewById(R.id.otp3);
        EditText otp4 = dialog.findViewById(R.id.otp4);
        //Button btnSubmitOtp = dialog.findViewById(R.id.btnSubmitOtp);

        // Xử lý nút Submit
//        btnSubmitOtp.setOnClickListener(v -> {
//            String otpCode = otp1.getText().toString() + otp2.getText().toString()
//                    + otp3.getText().toString() + otp4.getText().toString();
//
//            if (otpCode.length() == 4) {
//                // Xử lý mã OTP tại đây
//                dialog.dismiss(); // Đóng dialog
//            } else {
//                otp1.setError("Invalid OTP");
//            }
//        });

        // Xử lý nút Cancel
        Button btnCancel = dialog.findViewById(R.id.btnCancel_ConfirmOTP);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Hiển thị Dialog
        dialog.show();
    }
}