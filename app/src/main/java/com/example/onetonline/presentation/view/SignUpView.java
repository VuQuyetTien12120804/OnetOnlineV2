package com.example.onetonline.presentation.view;

public interface SignUpView {
    String getEmail();
    String getUserName();
    String getPassword();
    String getConfirmPassword();
    String getOTP();
    void ShowMessage(String message);
    void convertContext();
}
