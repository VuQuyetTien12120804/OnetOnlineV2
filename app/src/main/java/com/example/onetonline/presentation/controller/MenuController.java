package com.example.onetonline.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.onetonline.business.AvatarUseCase;
import com.example.onetonline.business.UserData;
import com.example.onetonline.presentation.model.UserInf;
import com.example.onetonline.presentation.view.*;
import android.Manifest;

public class MenuController {
    private MenuGameView menuGameView;
    private AvatarUseCase avatarUseCase;
    private UserData userData;
    private Context context;
    private static final int STORAGE_PERMISSION_CODE = 100;
    public static final String DEFAULT_AVATAR_FILENAME = "avatar_image";
    private SharedPreferences sharedPreferences;

    public MenuController(Context context, MenuGameForm menuView) {
        this.menuGameView = menuView;
        avatarUseCase = new AvatarUseCase(context);
        this.context = context;
        userData = new UserData(context);
        // Initialize SharedPreferences for saving settings
        sharedPreferences = context.getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
    }

    public void loadUserData(){
        if(userData.hasRecords()){
            UserInf userInf = userData.getData();
            menuGameView.showUserName(userInf.userName());
            menuGameView.showLevel(userInf.level());
            menuGameView.showExp(userInf.exp(), UserData.expCap(userInf.level()));
        }
    }

    public void handleClassicClick(){
        menuGameView.onClassicClicked();
    }

    public void handleContinueClick(){
        menuGameView.onContinueClicked();
    }

    public void handleOnlineClick(){
        menuGameView.onContinueClicked();
    }

    public void handleSettingClick(){
        // Load current settings from SharedPreferences
        boolean isMusicOn = sharedPreferences.getBoolean("music", true); // Default: true
        boolean isSoundClickOn = sharedPreferences.getBoolean("sound_click", true); // Default: true
        // Show the settings dialog
        menuGameView.showSettingsDialog(isMusicOn, isSoundClickOn);
    }

    public void handleExitClick(){

    }

    public void handleHelpContinueClick(){
        DialogHelper.showScrollableAlertDialog(context);
    }
    public void saveSettings(boolean isMusicOn, boolean isSoundClickOn) {
        // Save settings to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("music", isMusicOn);
        editor.putBoolean("sound_click", isSoundClickOn);
        editor.apply();
        // Notify the view that settings were saved
        menuGameView.onSettingsSaved(isMusicOn, isSoundClickOn);
    }

    public void loadAvatar() {
        // Ưu tiên tải từ local
        String userName = menuGameView.getUserName();
        avatarUseCase.loadAvatarFromLocal(new AvatarUseCase.AvatarCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                menuGameView.showAvatar(bitmap); // Nếu local có, hiển thị ngay
            }
            @Override
            public void onFailure(String err) {
                // Nếu local không có, tải từ server
                avatarUseCase.loadAvatarFromServer(userName, new AvatarUseCase.AvatarCallBack() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        menuGameView.showAvatar(bitmap); // Hiển thị ảnh từ server
                    }
                    @Override
                    public void onFailure(String serverErr) {
                        menuGameView.showMessage("No avatar found");
                    }
                });
            }
        });
    }

    public void handleSaveAvatar(Bitmap bitmap) {
        String userName = menuGameView.getUserName();
        avatarUseCase.saveAvatar(bitmap, userName, new AvatarUseCase.AvatarCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                menuGameView.showAvatar(bitmap);
                menuGameView.showMessage("Avatar updated successfully");
            }
            @Override
            public void onFailure(String err) {
                menuGameView.showMessage(err);
            }
        });
    }

    public void handleChangeAvatar() {
        if (ContextCompat.checkSelfPermission(((MenuGameForm)menuGameView), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(((MenuGameForm)menuGameView), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        } else {
            ((MenuGameForm)menuGameView).openImagePicker();
        }
    }

}
