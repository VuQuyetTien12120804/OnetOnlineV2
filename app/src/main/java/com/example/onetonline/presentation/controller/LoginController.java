package com.example.onetonline.presentation.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.example.onetonline.business.LoginUseCase;
import com.example.onetonline.data.OTPRepo;
import com.example.onetonline.data.UserRepo;
import com.example.onetonline.data.token;
import com.example.onetonline.presentation.model.ChangePassRequest;
import com.example.onetonline.presentation.model.LoginRequest;
import com.example.onetonline.presentation.model.userOTP;
import com.example.onetonline.presentation.view.LoginForm;
import com.example.onetonline.presentation.view.LoginView;
import com.example.onetonline.presentation.view.WellComeScreen;

public class LoginController {
    private LoginView loginView;
    private LoginUseCase loginUseCase;

    public LoginController(LoginView loginView, Context context) {
        this.loginView = loginView;
        UserRepo userRepo = new UserRepo(context);
        OTPRepo otpRepo = new OTPRepo();
        loginUseCase = new LoginUseCase(userRepo, otpRepo);
    }

    public void handleBackToHome(Context context){
        Intent intent = new Intent(context, WellComeScreen.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public void handleLogin(){
        String userName = loginView.getUserName();
        String password = loginView.getPassword();
        LoginRequest loginRequest = new LoginRequest(userName, password);
        loginUseCase.login(loginRequest, new UserRepo.LoginCallBack() {
            @Override
            public void onSuccess(token t) {
//                loginView.showMessage(user.id());
//                loginView.showMessage(user.userName());
//                loginView.showMessage(user.email());
//                loginView.showMessage(String.valueOf(user.level()));
//                loginView.showMessage(user.lastUpdate());
//                loginView.convertContext(user);
            }

            @Override
            public void onFailure(String err) {
                loginView.showMessage(err);
            }
        });
    }

    public void handleSendOTP(){
        userOTP otp = new userOTP(loginView.getUserName());
        loginUseCase.sendOTP(otp, new OTPRepo.SendCallBack() {
            @Override
            public void onSuccess() {
                loginView.showMessage("The OTP has been sent to your email and is valid for 3 minutes. Please enter the code to proceed.");
                ((LoginForm) loginView).showOtpDialog();
            }

            @Override
            public void onFailure(String err) {
                loginView.showMessage(err);
            }
        });
    }

    public void handleReSendOTP(){
        userOTP otp = new userOTP(loginView.getUserName());
        loginUseCase.resendOTP(otp, new OTPRepo.ResendCallBack() {
            @Override
            public void onSuccess() {
                loginView.showMessage("The OTP has been sent to your email and is valid for 3 minutes. Please enter the code to proceed.");
            }

            @Override
            public void onFailure(String err) {
                loginView.showMessage(err);
            }
        });
    }

    public void handleVerifyOTP(Dialog dialog){
        String emailOrName = loginView.getUserName();
        String otp = loginView.getOTP();
        loginUseCase.verifyOTP(emailOrName, otp, new OTPRepo.VerifyCallBack() {
            @Override
            public void onSuccess() {
                ((LoginForm) loginView).showResetPasswordDialog();
                dialog.dismiss();
            }

            @Override
            public void onFailure(String err) {
                loginView.showMessage(err);
            }
        });
    }

    public void handleChangePassword(Dialog dialog){
        String emailOrName = loginView.getUserName();
        String newPassword = loginView.getNewPassword();
        String confirmPassword = loginView.getConfirmPassword();
        ChangePassRequest changePassRequest = new ChangePassRequest(emailOrName, newPassword);
        loginUseCase.changePassword(confirmPassword, changePassRequest, new UserRepo.ChangePasswordCallBack() {
            @Override
            public void onSuccess() {
                loginView.showMessage("Password change successfully");
                dialog.dismiss();
            }

            @Override
            public void onFailure(String err) {
                loginView.showMessage(err);
            }
        });
    }
}