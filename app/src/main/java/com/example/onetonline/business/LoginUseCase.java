package com.example.onetonline.business;

import static com.example.onetonline.utils.Constants.DEFAULT_AVATAR_FILENAME;

import android.graphics.Bitmap;
import com.example.onetonline.broadcast.NetworkReceiver;
import com.example.onetonline.data.AvatarManager;
import com.example.onetonline.data.AvatarRepo;
import com.example.onetonline.data.OTPRepo;
import com.example.onetonline.data.User;
import com.example.onetonline.data.UserRepo;
import com.example.onetonline.data.token;
import com.example.onetonline.presentation.model.ChangePassRequest;
import com.example.onetonline.presentation.model.LoginRequest;
import com.example.onetonline.presentation.model.userOTP;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginUseCase {
    private UserRepo userRepo;
    private OTPRepo otpRepo;
    private AvatarRepo avatarRepo;

    public LoginUseCase(UserRepo userRepo, OTPRepo otpRepo, AvatarRepo avatarRepo) {
        this.userRepo = userRepo;
        this.otpRepo = otpRepo;
        this.avatarRepo = avatarRepo;
    }

    public void login(LoginRequest loginRequest, UserRepo.GetUserCallBack callBack){
        if (loginRequest.email().isEmpty() || loginRequest.password().isEmpty()) {
            callBack.onFailure("Please enter username and password");
            return;
        }
        if(!Checker.checkUserNameLen(loginRequest.email())) {
            callBack.onFailure("User Name must be more than 5 characters!");
            return;
        }
        if (!Checker.checkPassLen(loginRequest.password())) {
            callBack.onFailure("Password must be more than 6 characters!");
            return;
        }
        userRepo.login(loginRequest, new UserRepo.LoginCallBack() {
            @Override
            public void onSuccess(token t) {
                userRepo.saveToken(t.access_token());
                String access_token = "Bearer " + t.access_token();
                ExecutorService executor = Executors.newFixedThreadPool(1);
                executor.submit(() -> {
                    User newUser = null;
                    userRepo.getUser(access_token, new UserRepo.GetUserCallBack() {
                        @Override
                        public void onSuccess(User user) {
                            userRepo.insertToLocal(user);
                            callBack.onSuccess(user);
                            avatarRepo.getAvatar(user.id(), new AvatarRepo.GetCallBack() {
                                @Override
                                public void onSuccess(Bitmap bitmap) {
                                    userRepo.saveAvatar(bitmap, DEFAULT_AVATAR_FILENAME, new AvatarManager.AvatarCallback() {
                                        @Override
                                        public void onSuccess(Bitmap bitmap) {

                                        }

                                        @Override
                                        public void onFailure(String err) {

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(String err) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(String err) {
                            callBack.onFailure(err);
                        }
                    });
                });

            }

            @Override
            public void onFailure(String err) {
                if(err.equals("401")){
                    callBack.onFailure("Incorrect password!");
                    return;
                }
                callBack.onFailure(err);
            }
        });
    }

    public void sendOTP(userOTP otp, OTPRepo.SendCallBack callBack){
        otpRepo.sendOTP(otp, new OTPRepo.SendCallBack() {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onFailure(String err) {
                if(err.equals("409")){
                    resendOTP(otp, new OTPRepo.ResendCallBack() {
                        @Override
                        public void onSuccess() {
                            callBack.onSuccess();
                        }

                        @Override
                        public void onFailure(String err) {
                            callBack.onFailure(mapError(err));
                        }
                    });
                }
                callBack.onFailure(mapError(err));
            }
        });
    }

    public void resendOTP(userOTP otp,  OTPRepo.ResendCallBack callBack){
        otpRepo.resendOTP(otp, new OTPRepo.ResendCallBack() {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onFailure(String err) {
                if(err.equals("404")){
                    sendOTP(otp, new OTPRepo.SendCallBack() {
                        @Override
                        public void onSuccess() {
                            callBack.onSuccess();
                        }

                        @Override
                        public void onFailure(String err) {
                            callBack.onFailure(mapError(err));
                        }
                    });
                }
                callBack.onFailure(mapError(err));
            }
        });
    }

    public void verifyOTP(String email, String otp, OTPRepo.VerifyCallBack callBack){
        otpRepo.verifyOTP(email, otp, new OTPRepo.VerifyCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String err) {
                if(err.equals("401")){
                    callBack.onFailure("Incorrect password!");
                    return;
                }
                callBack.onFailure(err);
            }
        });
    }

    public void changePassword(String confirmPassword, ChangePassRequest changePassRequest, UserRepo.ChangePasswordCallBack callBack){
        if(!Checker.checkPassLen(changePassRequest.newPassword())){
            callBack.onFailure("Password must be more than 6 characters!");
            return;
        }
        if(!Checker.checkConfirmPassword(changePassRequest.newPassword(), confirmPassword)){ // kiem tra xem pass moi va pass xac nhan co bang nhau khong
            callBack.onFailure("Passwords do not match!");
            return;
        }
        userRepo.changePass(changePassRequest, new UserRepo.ChangePasswordCallBack() {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onFailure(String err) {
                callBack.onFailure(mapError((err)));
            }
        });
    }

    private String mapError(String errorCode) {
        switch (errorCode) {
            case "400": return "Error! The email or username already exists";
            case "404": return "Account doesn't exist!";
            default: return "Error: " + errorCode;
        }
    }

    public void close(){
        userRepo.close();
    }
}
