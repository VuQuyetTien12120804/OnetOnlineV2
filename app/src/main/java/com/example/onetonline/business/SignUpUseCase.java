package com.example.onetonline.business;

import static com.example.onetonline.utils.Constants.now;

import android.graphics.Bitmap;

import com.example.onetonline.data.OTPRepo;
import com.example.onetonline.data.PostResponse;
import com.example.onetonline.data.User;
import com.example.onetonline.data.UserRepo;
import com.example.onetonline.presentation.model.SignupRequest;
import com.example.onetonline.presentation.model.userOTP;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpUseCase {
    private final UserRepo userRepo;
    private final OTPRepo otpRepo;
    private final AvatarUseCase avatarUseCase;

    public SignUpUseCase(UserRepo userRepo, OTPRepo otpRepo, AvatarUseCase avatarUseCase) {
        this.userRepo = userRepo;
        this.otpRepo = otpRepo;
        this.avatarUseCase = avatarUseCase;
    }

    public interface SignUpResultCallback {
        void onInputInvalid(String error);
        void onInputValid();
    }

    public void sendOTP(String userName, String email, String password, String confirmPassword,SignUpResultCallback signUpResultCallback, OTPRepo.SendCallBack callBack){
        if (!Checker.checkUserNameLen(userName)) {
            signUpResultCallback.onInputInvalid("User Name must be more than 5 characters!");
            return;
        }
        if (!Checker.checkEmail(email)) {
            signUpResultCallback.onInputInvalid("Invalid email address");
            return;
        }
        if (!Checker.checkPassLen(password)) {
            signUpResultCallback.onInputInvalid("Password must be more than 6 characters!");
            return;
        }
        if (!Checker.checkConfirmPassword(password,confirmPassword)) {
            signUpResultCallback.onInputInvalid("Passwords do not match!");
            return;
        }

        signUpResultCallback.onInputValid();

        userOTP otp = new userOTP(email);
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
                else{
                    callBack.onFailure(mapError(err));
                }
            }
        });
    }

    public void resendOTP(userOTP otp, OTPRepo.ResendCallBack callBack){
        otpRepo.resendOTP(otp, new OTPRepo.ResendCallBack() {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onFailure(String err) {
                if(err.equals("404")){
                    otpRepo.sendOTP(otp, new OTPRepo.SendCallBack() {
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
                else{
                    callBack.onFailure(mapError(err));
                }
            }
        });
    }

    public void signUp(SignupRequest signupRequest, String otp, UserRepo.SignUpCallBack callBack) {
        if (!Checker.checkOTPLen(otp)) {
            callBack.onFailure("Enter a valid 6-digit OTP!");
            return;
        }
        otpRepo.verifyOTP(signupRequest.email(), otp, new OTPRepo.VerifyCallBack() {
            @Override
            public void onSuccess() {
                if (userRepo.hasRecords()) {
                    linkAccount(signupRequest, new UserRepo.SignUpCallBack() {
                        @Override
                        public void onSuccess(PostResponse postResponse) {
                            callBack.onSuccess(postResponse);
                        }

                        @Override
                        public void onFailure(String err) {
                            callBack.onFailure(mapError(err));
                        }
                    });
                }
                else{
                    newAccount(signupRequest, new UserRepo.SignUpCallBack() {
                        @Override
                        public void onSuccess(PostResponse postResponse) {
                            callBack.onSuccess(postResponse);
                        }

                        @Override
                        public void onFailure(String err) {
                            callBack.onFailure(mapError(err));
                        }
                    });
                }
            }

            @Override
            public void onFailure(String err) {
                callBack.onFailure(mapError(err));
            }
        });
    }

    public void newAccount(SignupRequest signupRequest, UserRepo.SignUpCallBack callBack){
        userRepo.addUser(signupRequest, new UserRepo.SignUpCallBack() {
            @Override
            public void onSuccess(PostResponse postResponse) {
                callBack.onSuccess(postResponse);
            }

            @Override
            public void onFailure(String err) {
                callBack.onFailure(mapError(err));
            }
        });
    }

    public void linkAccount(SignupRequest signupRequest, UserRepo.SignUpCallBack callBack){
        userRepo.addUser(signupRequest, new UserRepo.SignUpCallBack() {
            @Override
            public void onSuccess(PostResponse postResponse) {
                ExecutorService executor = Executors.newFixedThreadPool(1);
                executor.submit(() -> {
                    avatarUseCase.loadAvatarToServer(new AvatarUseCase.AvatarCallBack() {
                        @Override
                        public void onSuccess(Bitmap bitmap) {

                        }

                        @Override
                        public void onFailure(String err) {

                        }
                    });
                    User user = userRepo.getUserLocal();
                    user.setUserName(signupRequest.userName());
                    user.setLastUpdate(now());
                    userRepo.deleteFromLocal(user.id());
                    user.setId(postResponse.user_id());
                    userRepo.insertToLocal(user);
                    userRepo.sync(user, new UserRepo.UpdateUserCallBack() {
                        @Override
                        public void onSuccess() {
                            callBack.onSuccess(postResponse);
                        }

                        @Override
                        public void onFailure(String err) {
                            callBack.onFailure(mapError(err));
                        }
                    });
                });
                executor.shutdown();
            }

            @Override
            public void onFailure(String err) {

            }
        });
    }

    private String mapError(String errorCode) {
        switch (errorCode) {
            case "400": return "Error! The email or username already exists";
            case "401": return "Incorrect OTP!";
            case "404": return "Account doesn't exist!";
            case "409": return "Please wait";
            default: return "Error: " + errorCode;
        }
    }
}
