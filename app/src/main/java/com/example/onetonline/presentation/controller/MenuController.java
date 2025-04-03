package com.example.onetonline.presentation.controller;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.onetonline.business.AvatarRepo;
import com.example.onetonline.presentation.model.MenuModel;
import com.example.onetonline.presentation.view.*;
import com.example.onetonlinev2.R;

public class MenuController {
    private MenuModel menuModel;
    private MenuGame view;
    private AvatarRepo avatarRepo;

    public MenuController(MenuModel menuModel, MenuGame view) {
        this.menuModel = menuModel;
        this.view = view;
    }

}
