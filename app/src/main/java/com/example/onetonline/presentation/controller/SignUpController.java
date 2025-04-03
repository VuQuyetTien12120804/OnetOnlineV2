package com.example.onetonline.presentation.controller;

import android.content.Context;
import com.example.onetonline.business.OTPRepo;
import com.example.onetonline.business.PostResponse;
import com.example.onetonline.presentation.model.SignupRequest;
import com.example.onetonline.business.UserRepo;
import com.example.onetonline.presentation.model.userOTP;
import com.example.onetonline.presentation.view.SignUpView;

public class SignUpController {
    private SignUpView sign;
    private OTPRepo otpRepo;
    private UserRepo userRepo;

    public SignUpController(SignUpView sign, Context context) {
        this.sign = sign;
        otpRepo = new OTPRepo();
        userRepo = new UserRepo(context);
    }

    public void handleSendOTP(){
        userOTP otp = new userOTP(sign.getEmail());
        otpRepo.sendOTP(otp, new OTPRepo.SendCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String err) {
                if(err.equals("409")){
                    String email = sign.getEmail();
                    userOTP userotp = new userOTP(email);
                    otpRepo.resendOTP(userotp, new OTPRepo.ResendCallBack() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(String err) {
                            sign.showMessage(err);
                        }
                    });
                }
                else{
                    sign.showMessage(err);
                }
            }
        });
    }

    public void handleReSendOTP(){
        userOTP otp = new userOTP(sign.getEmail());
        otpRepo.resendOTP(otp, new OTPRepo.ResendCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String err) {
                if(err.equals("404")){
                    otpRepo.sendOTP(otp, new OTPRepo.SendCallBack() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(String err) {

                        }
                    });
                }
            }
        });
    }

    public void handleSignUp(){
        String email = sign.getEmail();
        String otp = sign.getOTP();
        SignupRequest signupRequest = new SignupRequest(sign.getEmail(), sign.getUserName(), sign.getPassword());

        otpRepo.verifyOTP(email, otp, new OTPRepo.VerifyCallBack() {
            @Override
            public void onSuccess() {
                userRepo.addUser(signupRequest, new UserRepo.SignUpCallBack() {
                    @Override
                    public void onSuccess(PostResponse postResponse) {
                        sign.convertContext();
                        sign.showMessage("Sign up successfully, Login your account please!!");
                    }

                    @Override
                    public void onFailure(String err) {
                        if(err.equals("400")){
                            sign.showMessage("Error! The email or username already exists");
                        }
                    }
                });
            }

            @Override
            public void onFailure(String err) {
                if(err.equals("401")){
                    sign.showMessage("Wrong otp");
                }
                else{
                    sign.showMessage(err);
                }
            }
        });
    }
}
