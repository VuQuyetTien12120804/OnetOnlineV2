package com.example.onetonline.business;

import com.example.onetonline.data.OTPRepo;
import com.example.onetonline.data.PostResponse;
import com.example.onetonline.data.UserRepo;
import com.example.onetonline.presentation.model.SignupRequest;
import com.example.onetonline.presentation.model.userOTP;

public class SignUpUseCase {
    private final UserRepo userRepo;
    private final OTPRepo otpRepo;

    public SignUpUseCase(UserRepo userRepo, OTPRepo otpRepo) {
        this.userRepo = userRepo;
        this.otpRepo = otpRepo;
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

    public void signUp(SignupRequest signupRequest, String otp, UserRepo.SignUpCallBack callBack){
        if (!Checker.checkOTPLen(otp)) { // Giả sử OTP là 6 ký tự
            callBack.onFailure("Enter a valid 6-digit OTP!");
            return;
        }
        otpRepo.verifyOTP(signupRequest.email(), otp, new OTPRepo.VerifyCallBack() {
            @Override
            public void onSuccess() {
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

            @Override
            public void onFailure(String err) {
                callBack.onFailure(mapError(err));
            }
        });
    }

    private String mapError(String errorCode) {
        switch (errorCode) {
            case "400": return "Error! The email or username already exists";
            case "401": return "Incorrect OTP!";
            case "404": return "Account doesn't exist!";
            default: return "Error: " + errorCode;
        }
    }
}
