package com.example.onetonline.presentation.view;

public interface SignUpView {
    String getEmail();
    String getUserName();
    String getPassword();
    String getConfirmPassword();
    String getOTP();
    void showMessage(String message);
    void navigateTo(Class<?> activityClass);
}
