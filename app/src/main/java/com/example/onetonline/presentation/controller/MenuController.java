package com.example.onetonline.presentation.controller;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.onetonline.business.AvatarUseCase;
import com.example.onetonline.presentation.view.*;
import android.Manifest;

public class MenuController {
    private MenuGame menuView;
    private AvatarUseCase avatarUseCase;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final String DEFAULT_AVATAR_FILENAME = "avatar_image";

    public MenuController(Context context, MenuGame menuView) {
        this.menuView = menuView;
        avatarUseCase = new AvatarUseCase(context);
    }

    public void loadAvatar() {
        // Ưu tiên tải từ local
        avatarUseCase.loadAvatarFromLocal(DEFAULT_AVATAR_FILENAME, new AvatarUseCase.AvatarCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                menuView.showAvatar(bitmap); // Nếu local có, hiển thị ngay
            }
            @Override
            public void onFailure(String err) {
                // Nếu local không có, tải từ server
                avatarUseCase.loadAvatarFromServer("Dzunk26", DEFAULT_AVATAR_FILENAME, new AvatarUseCase.AvatarCallBack() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        menuView.showAvatar(bitmap); // Hiển thị ảnh từ server
                    }
                    @Override
                    public void onFailure(String serverErr) {
                        menuView.showMessage("No avatar found");
                    }
                });
            }
        });
    }

    public void handleSaveAvatar(Bitmap bitmap) {
        avatarUseCase.saveAvatar(bitmap, DEFAULT_AVATAR_FILENAME, "Dzunk26", new AvatarUseCase.AvatarCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                menuView.showAvatar(bitmap);
                menuView.showMessage("Avatar updated successfully");
            }
            @Override
            public void onFailure(String err) {
                menuView.showMessage(err);
            }
        });
    }

    public void handleChangeAvatar() {
        if (ContextCompat.checkSelfPermission(menuView, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(menuView,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        } else {
            menuView.openImagePicker();
        }
    }
}
