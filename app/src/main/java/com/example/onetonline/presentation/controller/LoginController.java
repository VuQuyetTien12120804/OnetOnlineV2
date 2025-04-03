package com.example.onetonline.presentation.controller;

import android.app.Dialog;
import android.content.Context;
import com.example.onetonline.business.OTPRepo;
import com.example.onetonline.business.PostResponse;
import com.example.onetonline.business.User;
import com.example.onetonline.business.UserRepo;
import com.example.onetonline.business.token;
import com.example.onetonline.presentation.model.ChangePassRequest;
import com.example.onetonline.presentation.model.LoginRequest;
import com.example.onetonline.presentation.model.userOTP;
import com.example.onetonline.presentation.view.LoginForm;
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
                    loginView.showMessage("Incorrect password");
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
                ((LoginForm) loginView).showCustomToast("The OTP has been sent to your email and is valid for 3 minutes. Please enter the code to proceed.");
                ((LoginForm) loginView).showOtpDialog();
            }

            @Override
            public void onFailure(String err) {
                if(err.equals("409")){
                    String userName = loginView.getUserName();
                    userOTP userotp = new userOTP(userName);
                    otpRepo.resendOTP(userotp, new OTPRepo.ResendCallBack() {
                        @Override
                        public void onSuccess() {
                            ((LoginForm) loginView).showCustomToast("The OTP has been sent to your email and is valid for 3 minutes. Please enter the code to proceed.");
                            ((LoginForm) loginView).showOtpDialog();
                        }

                        @Override
                        public void onFailure(String err) {
                            if(err.equals("404")){
                                loginView.showMessage("Account doesn't exists");
                            }
                        }
                    });
                }
                else if(err.equals("404")){
                    loginView.showMessage("Account doesn't exists");
                    return ;
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
                ((LoginForm) loginView).showCustomToast("The OTP has been sent to your email and is valid for 3 minutes. Please enter the code to proceed.");
            }

            @Override
            public void onFailure(String err) {
                if(err.equals("404")){
                    otpRepo.sendOTP(otp, new OTPRepo.SendCallBack() {
                        @Override
                        public void onSuccess() {
                            ((LoginForm) loginView).showCustomToast("The OTP has been sent to your email and is valid for 3 minutes. Please enter the code to proceed.");
                        }

                        @Override
                        public void onFailure(String err) {
                            if(err.equals("404")){
                                loginView.showMessage("Account doesn't exists");
                            }
                        }
                    });
                }
            }
        });
    }

    public void handleVerifyOTP(Dialog dialog){
        String emailOrName = loginView.getUserName();
        String otp = loginView.getOTP();

        otpRepo.verifyOTP(emailOrName, otp, new OTPRepo.VerifyCallBack() {
            @Override
            public void onSuccess() {
                ((LoginForm) loginView).showResetPasswordDialog();
                dialog.dismiss();
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

    public void handleChangePassword(Dialog dialog){
        String emailOrName = loginView.getUserName();
        String newPassword = loginView.getNewPassword();
        ChangePassRequest changePassRequest = new ChangePassRequest(emailOrName, newPassword);
        userRepo.changePass(changePassRequest, new UserRepo.UpdateUserCallBack() {
            @Override
            public void onSuccess() {
                loginView.showMessage("Password change successfully");
                dialog.dismiss();
            }

            @Override
            public void onFailure(String err) {
                if(err.equals("404")){
                    loginView.showMessage("Account doesn't exists");
                } else {
                    loginView.showMessage(err);
                }
            }
        });
    }
}
