package com.example.onetonline.presentation.controller;

import android.content.Context;
import com.example.onetonline.business.OTPRepo;
import com.example.onetonline.business.PostResponse;
import com.example.onetonline.business.User;
import com.example.onetonline.business.UserRepo;
import com.example.onetonline.business.token;
import com.example.onetonline.presentation.model.LoginRequest;
import com.example.onetonline.presentation.model.userOTP;
import com.example.onetonline.presentation.view.LoginView;

public class LoginController {
    private LoginView loginView;
    private UserRepo userRepo;
    private OTPRepo otpRepo;

    public LoginController(LoginView loginView, Context context) {
        this.loginView = loginView;
        userRepo = new UserRepo(context);
        otpRepo = new OTPRepo();
    }

    public void handleLogin(){
        LoginRequest loginRequest = new LoginRequest(loginView.getUserName(), loginView.getPassword());
        if (loginView.getUserName().isEmpty() || loginView.getPassword().isEmpty()) {
            loginView.showMessage("Please enter username and password");
            return;
        }
        userRepo.login(loginRequest, new UserRepo.LoginCallBack() {
            @Override
            public void onSuccess(token t) {
                String access_token = "Bearer " + t.access_token();
                userRepo.getUser(access_token, new UserRepo.GetUserCallBack() {
                    @Override
                    public void onSuccess(User user) {
                        loginView.showMessage(user.id());
                        loginView.showMessage(user.userName());
                        loginView.showMessage(user.email());
                        loginView.showMessage(String.valueOf(user.level()));
                        loginView.showMessage(user.lastUpdate());
                        loginView.convertContext(user);
                    }

                    @Override
                    public void onFailure(String err) {

                    }
                });
            }

            @Override
            public void onFailure(String err) {
                if(err.equals("401")){
                    loginView.showMessage("Wrong password");
                }
                else if(err.equals("404")){
                    loginView.showMessage("Account doesn't exists");
                }
                else {
                    loginView.showMessage(err);
                }
            }
        });
    }

    public void handleSendOTP(){
        userOTP otp = new userOTP(loginView.getUserName());
        otpRepo.sendOTP(otp, new OTPRepo.SendCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String err) {
                if(err.equals("409")){
                    String userName = loginView.getUserName();
                    userOTP userotp = new userOTP(userName);
                    otpRepo.resendOTP(userotp, new OTPRepo.ResendCallBack() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(String err) {
                            loginView.showMessage(err);
                        }
                    });
                }
                else{
                    loginView.showMessage(err);
                }
            }
        });
    }

    public void handleReSendOTP(){
        userOTP otp = new userOTP(loginView.getUserName());
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

    public void handleVerifyOTP(){
        String userName = loginView.getUserName();
        String otp = "";

        otpRepo.verifyOTP(userName, otp, new OTPRepo.VerifyCallBack() {
            @Override
            public void onSuccess() {
//                loginView.
            }

            @Override
            public void onFailure(String err) {
                if (err.equals("401")) {
                    loginView.showMessage("Wrong otp");
                } else {
                    loginView.showMessage(err);
                }
            }
        });
    }
}
