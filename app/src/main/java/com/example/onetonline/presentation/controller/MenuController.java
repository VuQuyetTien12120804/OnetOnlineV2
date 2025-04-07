package com.example.onetonline.presentation.controller;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.onetonline.business.AvatarUseCase;
import com.example.onetonline.business.MenuGameUseCase;
import com.example.onetonline.business.UserData;
import com.example.onetonline.data.User;
import com.example.onetonline.presentation.model.UserInf;
import com.example.onetonline.presentation.view.*;
import android.Manifest;

public class MenuController {
    private MenuGameView menuGameView;
    private AvatarUseCase avatarUseCase;
    private UserData userData;
    private MenuGameUseCase menuGameUseCase;
    private static final int STORAGE_PERMISSION_CODE = 100;
    public static final String DEFAULT_AVATAR_FILENAME = "avatar_image";

    public MenuController(Context context, MenuGameForm menuView) {
        this.menuGameView = menuView;
        avatarUseCase = new AvatarUseCase(context);
        userData = new UserData(context);
    }

    public void loadUserData(){
        UserInf userInf = userData.getData();
        menuGameView.showUserName(userInf.userName());
        menuGameView.showLevel(userInf.level());
        menuGameView.showExp(userInf.exp(), menuGameUseCase.getExpCap(userInf.level()));
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
        menuGameView.onSettingClicked();
    }

    public void handleExitClick(){

    }

    public void handleHelpClassicClick(){

    }

    public void handleHelpContinueClick(){

    }

    public void handleHelpOnlineClick(){

    }

    public void loadAvatar() {
        // Ưu tiên tải từ local
        String userName = menuGameView.getUserName();
        avatarUseCase.loadAvatarFromLocal(DEFAULT_AVATAR_FILENAME, new AvatarUseCase.AvatarCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                menuGameView.showAvatar(bitmap); // Nếu local có, hiển thị ngay
            }
            @Override
            public void onFailure(String err) {
                // Nếu local không có, tải từ server
                avatarUseCase.loadAvatarFromServer(userName, DEFAULT_AVATAR_FILENAME, new AvatarUseCase.AvatarCallBack() {
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
        avatarUseCase.saveAvatar(bitmap, DEFAULT_AVATAR_FILENAME, userName, new AvatarUseCase.AvatarCallBack() {
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
