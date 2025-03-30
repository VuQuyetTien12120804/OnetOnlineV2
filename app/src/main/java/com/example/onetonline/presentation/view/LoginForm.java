package com.example.onetonline.presentation.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onetonline.business.User;
import com.example.onetonline.presentation.controller.LoginController;
import com.example.onetonline.presentation.model.Checker;
import com.example.onetonlinev2.R;
import com.mukeshsolanki.OtpView;

public class LoginForm extends AppCompatActivity implements LoginView{
    //Khai báo
    private Button btnBack, btnLogin;
    private EditText etLogin, etPassword;
    private LoginController loginController;
    private Button btnBackLoginForm;
    private TextView tvForgotPassword;
    private Dialog loadingDialog;
    private String otp = "";

    public void initWidgets(){
        btnBack = findViewById(R.id.btnBackLoginForm);
        btnLogin = findViewById(R.id.btnLogin);
        etLogin = findViewById(R.id.etNameOrEmail);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnBackLoginForm = findViewById(R.id.btnBackLoginForm);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
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

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginController.handleSendOTP();
                showOtpDialog();
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

    private void showOtpDialog() {
        // Tạo Dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.confirm_otp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));// Xóa nen sau
        dialog.setCancelable(true); // Có thể tắt dialog khi nhấn bên ngoài

        OtpView otpView = dialog.findViewById(R.id.otp_view);

        // Xử lý nút Cancel
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Xử lý nút Resend
        Button btnResend = dialog.findViewById(R.id.btnResend_ConfirmOTP);
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginForm.this, "The OTP has been sent to your email and is valid for 3 minutes. Please enter the code to proceed.", Toast.LENGTH_SHORT).show();
                loginController.handleReSendOTP();
            }
        });

        // Xử lý nút verify đây là nếu đúng thì chuyển sang reset password
        Button btnVerify = dialog.findViewById(R.id.btnVerify_ConfirmOTP);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = otpView.getText().toString();
                if(!Checker.checkOTPLen(otpView.getText().toString())){
                    Toast.makeText(LoginForm.this, "Enter your otp!", Toast.LENGTH_LONG).show();
                }
                else{
                    //Mo man hinh reset password
                    showResetPasswordDialog();
                    
                    //Dong dialog xac nhan  otp
                    dialog.dismiss();
                }
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
                //Mo man hinh loading
                showLoadingResetPasswordDialog();
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

    private void showLoadingResetPasswordDialog() {
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_waiting_reset_password);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.setCancelable(false); // Không cho người dùng tắt khi đang loading
        loadingDialog.show();

        // Chờ 3 giây rồi ẩn loading và thực hiện hành động
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingDialog.dismiss();
            navigateToLogin();
        }, 3000); // 3 giây
    }

    private void navigateToLogin() {
        // Xử lý logic chuyển màn hình login
        Toast.makeText(LoginForm.this, "Password has been reset successfully", Toast.LENGTH_SHORT).show();
    }
}