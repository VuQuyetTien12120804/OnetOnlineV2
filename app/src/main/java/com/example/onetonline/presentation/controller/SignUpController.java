package com.example.onetonline.presentation.controller;

import android.content.Context;

import com.example.onetonline.business.AvatarUseCase;
import com.example.onetonline.business.SignUpUseCase;
import com.example.onetonline.business.SyncUseCase;
import com.example.onetonline.data.AvatarRepo;
import com.example.onetonline.data.OTPRepo;
import com.example.onetonline.data.PostResponse;
import com.example.onetonline.data.TokenStorage;
import com.example.onetonline.presentation.model.SignupRequest;
import com.example.onetonline.data.UserRepo;
import com.example.onetonline.presentation.model.userOTP;
import com.example.onetonline.presentation.view.LoginForm;
import com.example.onetonline.presentation.view.SignUpForm;
import com.example.onetonline.presentation.view.SignUpView;

public class SignUpController {
    private SignUpView signUpView;
    private SignUpUseCase signUpUseCase;

    public SignUpController(SignUpView signUpView, Context context) {
        this.signUpView = signUpView;
        UserRepo userRepo = new UserRepo(context);
        OTPRepo otpRepo = new OTPRepo();
        AvatarUseCase avatarUseCase = new AvatarUseCase(context);
        signUpUseCase = new SignUpUseCase(userRepo, otpRepo, avatarUseCase);
    }

    public void handleBackToHome(Class<?> activityClass){
        signUpView.navigateTo(activityClass);
    }

    public void handleBackToLogin(Class<?> activityClass){
        signUpView.navigateTo(activityClass);
    }

    public void handleSendOTP(){
        String userName = signUpView.getUserName();
        String email = signUpView.getEmail();
        String password = signUpView.getPassword();
        String confirmPassword = signUpView.getConfirmPassword();
        signUpUseCase.sendOTP(userName, email, password, confirmPassword, new SignUpUseCase.SignUpResultCallback() {
            @Override
            public void onInputInvalid(String error) {
                signUpView.showMessage(error);
            }

            @Override
            public void onInputValid() {
                ((SignUpForm) signUpView).showOtpDialog();
            }
        }, new OTPRepo.SendCallBack() {
            @Override
            public void onSuccess() {
                signUpView.showMessage("The OTP has been sent to your email and is valid for 3 minutes. Please enter the code to proceed.");
            }

            @Override
            public void onFailure(String err) {
                signUpView.showMessage(err);
            }
        });
    }

    public void handleReSendOTP(){
        String email = signUpView.getEmail();
        userOTP otp = new userOTP(email);
        signUpUseCase.resendOTP(otp, new OTPRepo.ResendCallBack() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(String err) {
            }
        });
    }

    public void handleSignUp(){
        String email = signUpView.getEmail();
        String otp = signUpView.getOTP();
        SignupRequest signupRequest = new SignupRequest(signUpView.getEmail(), signUpView.getUserName(), signUpView.getPassword());
        signUpUseCase.signUp(signupRequest, otp, new UserRepo.SignUpCallBack() {
            @Override
            public void onSuccess(PostResponse postResponse) {
                signUpView.showMessage("Sign up successfully, please log in to your account!");
                signUpView.navigateTo(LoginForm.class);
            }

            @Override
            public void onFailure(String err) {
                signUpView.showMessage(err);
            }
        });
    }

    public void handleBackToLogin(){
        signUpView.navigateTo(LoginForm.class);

    }
}
