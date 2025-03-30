package com.example.onetonline.presentation.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_form);

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
                showCustomToast("The OTP has been sent to your email and is valid for 5 minutes. Please enter the code to proceed.");
                showOtpDialog();
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
                showCustomToast("The OTP has been sent to your email and is valid for 5 minutes. Please enter the code to proceed.");
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
                showCustomToast("Please fill in all fields.");
            } else if (!Password.equals(confirmPasswordText)) { // kiem tra xem pass moi va pass xac nhan co bang nhau khong
                showCustomToast("Passwords do not match.");
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
        showCustomToast("Password has been reset successfully");
    }
}