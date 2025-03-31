package com.example.onetonline.presentation.model;

public class ChangePassRequest {
    private String emailOrName;
    private String newPassword;

    public ChangePassRequest(String emailOrName, String newPassword) {
        this.emailOrName = emailOrName;
        this.newPassword = newPassword;
    }

    public String emailOrName() {
        return emailOrName;
    }

    public ChangePassRequest setEmailOrName(String emailOrName) {
        this.emailOrName = emailOrName;
        return this;
    }

    public String newPassword() {
        return newPassword;
    }

    public ChangePassRequest setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }
}
