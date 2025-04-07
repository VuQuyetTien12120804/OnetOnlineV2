package com.example.onetonline.presentation.view;

import android.graphics.Bitmap;

public interface MenuGameView {
    void onClassicClicked();
    void onContinueClicked();
    void onOnlineClicked();
    void onExitClicked();
    void onHelpClassicClicked();
    void onHelpContinueClicked();
    void onSettingClicked();
    void showAvatar(Bitmap bitmap);
    void showMessage(String message);
}
