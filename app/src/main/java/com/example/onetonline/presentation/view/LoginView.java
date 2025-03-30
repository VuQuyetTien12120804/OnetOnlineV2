package com.example.onetonline.presentation.view;

import com.example.onetonline.business.User;

public interface LoginView {
    String getUserName();
    String getPassword();
    void showMessage(String message);
    void convertContext(User user);
}
