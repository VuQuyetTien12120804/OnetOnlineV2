package com.example.onetonline.presentation.model;

public class SignupRequest {
    private String email;
    private String userName;
    private String password;

    public SignupRequest(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public String userName() {
        return userName;
    }

    public SignupRequest setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String email() {
        return email;
    }

    public SignupRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String password() {
        return password;
    }

    public SignupRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
