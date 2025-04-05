package com.example.onetonline.presentation.view;

import android.graphics.Bitmap;

public interface MenuGameView {
    void showAvatar(Bitmap bitmap);
    void showMessage(String message);
    void navigateTo(Class<?> activityClass);
}
