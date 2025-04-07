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
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onetonline.broadcast.SyncService;
import com.example.onetonline.data.User;
import com.example.onetonline.presentation.controller.LoginController;
import com.example.onetonlinev2.R;
import com.mukeshsolanki.OtpView;

public class LoginForm extends AppCompatActivity implements LoginView{
    private Button btnBackLoginForm, btnLogin;
    private EditText etLogin, etPassword;
    private LoginController loginController;
    private TextView tvForgotPassword;
    private Dialog loadingDialog;
    private String otp = "", newPassword = "", confirmPassword = "";

    public void initWidgets(){
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

//        start service to sync user data with server
        Intent serviceIntent = new Intent(this, SyncService.class);
        startService(serviceIntent);

        btnBackLoginForm.setOnClickListener(v -> loginController.handleBackToHome(WellComeScreen.class));
        btnLogin.setOnClickListener(v -> loginController.handleLogin(MenuGameForm.class));
        tvForgotPassword.setOnClickListener(v -> loginController.handleSendOTP());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginController.onDestroy();
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
        showCustomToast(message);
    }

    @Override
    public void navigateTo(Class<?> activityClass) {
        Intent i = new Intent(LoginForm.this, activityClass);
        startActivity(i);
        finish();
    }

    @Override
    public void onLoginSuccess(User user) {
        Intent intent = new Intent(this, MenuGameForm.class);
        intent.putExtra("user_data", user);
        startActivity(intent);
        finish();
    }

    @Override
    public String getOTP() {
        return otp;
    }

    @Override
    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void showCustomToast(String message){
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

    public void showOtpDialog() {
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
        btnResend.setOnClickListener(v -> loginController.handleReSendOTP());

        // Xử lý nút verify đây là nếu đúng thì chuyển sang reset password
        Button btnVerify = dialog.findViewById(R.id.btnVerify_ConfirmOTP);
        btnVerify.setOnClickListener(v -> loginController.handleVerifyOTP(dialog));

        // Hiển thị Dialog
        dialog.show();
    }

    public void showResetPasswordDialog(){
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

        EditText etNewPassword = resetPasswordDialog.findViewById(R.id.et_new_password);
        EditText etConfirmPassword = resetPasswordDialog.findViewById(R.id.et_confirm_password);

        btnConfirmReset.setOnClickListener(v->{
            newPassword = etNewPassword.getText().toString();
            confirmPassword = etConfirmPassword.getText().toString();
            loginController.handleChangePassword(resetPasswordDialog);
        });

        ImageView toggleNewPassword = resetPasswordDialog.findViewById(R.id.iv_toggle_new_password);
        ImageView toggleConfirmPassword = resetPasswordDialog.findViewById(R.id.iv_toggle_confirm_password);

        // Xử lý nút toggle cho mật khẩu mới
        toggleNewPassword.setOnClickListener(v -> {
            if (etNewPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                toggleNewPassword.setImageResource(R.drawable.ic_visibility); // Icon hiển thị mật khẩu
            } else {
                etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                toggleNewPassword.setImageResource(R.drawable.ic_visibility_off); // Icon ẩn mật khẩu
            }
            etNewPassword.setSelection(etNewPassword.getText().length()); // Đặt con trỏ ở cuối văn bản
        });

        // Xử lý nút toggle cho xác nhận mật khẩu
        toggleConfirmPassword.setOnClickListener(v -> {
            if (etConfirmPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                toggleConfirmPassword.setImageResource(R.drawable.ic_visibility); // Icon hiển thị mật khẩu
            } else {
                etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                toggleConfirmPassword.setImageResource(R.drawable.ic_visibility_off); // Icon ẩn mật khẩu
            }
            etConfirmPassword.setSelection(etConfirmPassword.getText().length()); // Đặt con trỏ ở cuối văn bản
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