package com.example.onetonline.presentation.view;

import com.example.onetonline.data.User;

public interface LoginView {
    String getUserName();
    String getPassword();
    String getOTP();
    String getNewPassword();
    String getConfirmPassword();
    void showMessage(String message);
    void navigateTo(Class<?> activityClass);
    void onLoginSuccess(User user);
}
