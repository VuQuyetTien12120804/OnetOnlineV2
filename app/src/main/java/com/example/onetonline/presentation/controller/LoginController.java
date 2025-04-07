package com.example.onetonline.presentation.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.security.keystore.UserPresenceUnavailableException;

import com.example.onetonline.business.LoginUseCase;
import com.example.onetonline.data.AvatarRepo;
import com.example.onetonline.data.OTPRepo;
import com.example.onetonline.data.User;
import com.example.onetonline.data.UserRepo;
import com.example.onetonline.data.myDBHelper;
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
        AvatarRepo avatarRepo = new AvatarRepo();
        loginUseCase = new LoginUseCase(userRepo, otpRepo, avatarRepo);
    }

    public void handleBackToHome(Class<?> activityClass){
        loginView.navigateTo(activityClass);
    }

    public void handleLogin(Class<?> activityClass){
        String userName = loginView.getUserName();
        String password = loginView.getPassword();
        LoginRequest loginRequest = new LoginRequest(userName, password);
        loginUseCase.login(loginRequest, new UserRepo.GetUserCallBack() {
            @Override
            public void onSuccess(User user) {
                loginView.onLoginSuccess(user);
            }

            @Override
            public void onFailure(String err) {
                loginView.showMessage(err);
            }
        });
    }

    public void handleSendOTP(){
        ((LoginForm) loginView).showOtpDialog();
        userOTP otp = new userOTP(loginView.getUserName());
        loginUseCase.sendOTP(otp, new OTPRepo.SendCallBack() {
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

    public void onDestroy(){
        loginUseCase.close();
    }
}