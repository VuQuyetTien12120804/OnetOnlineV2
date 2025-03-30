package com.example.onetonline.presentation.model;

public class userOTP {
    private String email;
    private String otp;

    public userOTP(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public String email() {
        return email;
    }

    public void setEmail(String id) {
        this.email = id;
    }

    public String otp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
