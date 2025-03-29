package com.example.onetonline.presentation.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.onetonlinev2.R;

public class LoginForm extends AppCompatActivity {

    //Khai báo
    private Button btnBackLoginForm;
    private TextView tvForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.id_form_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ánh xa id
        btnBackLoginForm = findViewById(R.id.btnBackLoginForm);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Xử lý click
        btnBackLoginForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginForm.this, WellComeScreen.class);
                startActivity(intent);
                finish();
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginForm.this, "The OTP has been sent to your email and is valid for 5 minutes. Please enter the code to proceed.", Toast.LENGTH_SHORT).show();
                showOtpDialog();
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

        // Xử lý nút Resend
        Button btnResend = dialog.findViewById(R.id.btnResend_ConfirmOTP);
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginForm.this, "The OTP has been sent to your email and is valid for 5 minutes. Please enter the code to proceed.", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút verify đây là nếu đúng thì chuyển sang reset password
        Button btnVerify = dialog.findViewById(R.id.btnVerify_ConfirmOTP);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Mo man hinh reset password
                showResetPasswordDialog();

                //Dong dialog xac nhan  otp
                dialog.dismiss();
            }
        });

        // Hiển thị Dialog
        dialog.show();
    }
    private void showResetPasswordDialog(){
        // Tao dialog moi cho reset password
        Dialog resetPasswordDialog = new Dialog(this);
        resetPasswordDialog.setContentView(R.layout.dialog_reset_password);
        resetPasswordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        resetPasswordDialog.setCancelable(true);

        //Xử lý nút Cancel
        Button btnCancel = resetPasswordDialog.findViewById(R.id.btnCancel_Reset_Password);
        btnCancel.setOnClickListener(v -> resetPasswordDialog.dismiss());

        //Xử lý nút xác nhận
        Button btnConfirmReset = resetPasswordDialog.findViewById(R.id.btn_confirm_reset);

        EditText newPassword = resetPasswordDialog.findViewById(R.id.et_new_password);
        EditText confirmPassword = resetPasswordDialog.findViewById(R.id.et_confirm_password);

        btnConfirmReset.setOnClickListener(v->{

            String Password = newPassword.getText().toString();
            String confirmPasswordText = confirmPassword.getText().toString();

            //Kiem tra da dien du chua
            if(Password.isEmpty() || confirmPasswordText.isEmpty()){
                Toast.makeText(LoginForm.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!Password.equals(confirmPasswordText)) { // kiem tra xem pass moi va pass xac nhan co bang nhau khong
                Toast.makeText(LoginForm.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else { //dang ki thanh cong
                Toast.makeText(LoginForm.this, "Password has been reset successfully", Toast.LENGTH_SHORT).show();
                resetPasswordDialog.dismiss();
            }
        });

        ImageView toggleNewPassword = resetPasswordDialog.findViewById(R.id.iv_toggle_new_password);
        ImageView toggleConfirmPassword = resetPasswordDialog.findViewById(R.id.iv_toggle_confirm_password);

        // Xử lý nút toggle cho mật khẩu mới
        toggleNewPassword.setOnClickListener(v -> {
            if (newPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                toggleNewPassword.setImageResource(R.drawable.ic_visibility); // Icon hiển thị mật khẩu
            } else {
                newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                toggleNewPassword.setImageResource(R.drawable.ic_visibility_off); // Icon ẩn mật khẩu
            }
            newPassword.setSelection(newPassword.getText().length()); // Đặt con trỏ ở cuối văn bản
        });

        // Xử lý nút toggle cho xác nhận mật khẩu
        toggleConfirmPassword.setOnClickListener(v -> {
            if (confirmPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                toggleConfirmPassword.setImageResource(R.drawable.ic_visibility); // Icon hiển thị mật khẩu
            } else {
                confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                toggleConfirmPassword.setImageResource(R.drawable.ic_visibility_off); // Icon ẩn mật khẩu
            }
            confirmPassword.setSelection(confirmPassword.getText().length()); // Đặt con trỏ ở cuối văn bản
        });


        //hien thi dialog
        resetPasswordDialog.show();
    }
}