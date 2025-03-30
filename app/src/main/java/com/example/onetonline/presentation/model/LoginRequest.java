package com.example.onetonline.presentation.model;

public class LoginRequest {
    private String emailOrUserName;
    private String password;

    public LoginRequest(String emailOrUserName, String password) {
        this.emailOrUserName = emailOrUserName;
        this.password = password;
    }

    public String email() {
        return emailOrUserName;
    }

    public void setEmailOrUserName(String emailOrUserName) {
        this.emailOrUserName = emailOrUserName;
    }

    public String password() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
