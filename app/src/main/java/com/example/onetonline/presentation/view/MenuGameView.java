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
    void showUserName(String userName);
    void showLevel(int level);
    void showExp(int exp, int expCap);
    String getUserName();
    //method for settings
    void showSettingsDialog(boolean isMusicOn, boolean isSoundClickOn);
    void onSettingsSaved(boolean isMusicOn, boolean isSoundClickOn);

}
