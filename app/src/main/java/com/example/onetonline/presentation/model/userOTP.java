package com.example.onetonline.presentation.model;

public class userOTP {
    private String emailOrName;

    public userOTP(String emailOrName) {
        this.emailOrName = emailOrName;
    }

    public String email() {
        return emailOrName;
    }

    public void setEmailOrName(String emailOrName) {
        this.emailOrName = emailOrName;
    }
}
