package com.example.onetonline.presentation.view;

import android.graphics.Bitmap;

public interface MenuGameView {
    void showAvatar(Bitmap bitmap);
    void showMessage(String message);
    void showUserName(String userName);
    void showLevel(int level);
    void showExp(int exp, int expCap);
    String getUserName();
    void navigateTo(Class<?> activityClass);
}
