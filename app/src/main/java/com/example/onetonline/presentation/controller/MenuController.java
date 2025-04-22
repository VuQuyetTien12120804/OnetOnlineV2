package com.example.onetonline.presentation.controller;

import static com.example.onetonline.utils.Constants.STORAGE_PERMISSION_CODE;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.onetonline.business.AvatarUseCase;
import com.example.onetonline.business.UserData;
import com.example.onetonline.data.User;
import com.example.onetonline.data.UserRepo;
import com.example.onetonline.data.userRanking;
import com.example.onetonline.presentation.model.UserInf;
import com.example.onetonline.presentation.view.MenuGameForm;
import com.example.onetonline.presentation.view.MenuGameView;
import com.example.onetonline.presentation.view.WellComeScreen;

import java.util.ArrayList;
import java.util.List;

public class MenuController {
    private MenuGameView menuGameView;
    private AvatarUseCase avatarUseCase;
    private UserData userData;
    private Context context;

    public MenuController(Context context, MenuGameForm menuView) {
        this.menuGameView = menuView;
        avatarUseCase = new AvatarUseCase(context);
        this.context = context;
        userData = new UserData(context);
    }

    public void loadUserData(){
        if(userData.hasRecords()){
            UserInf userInf = userData.getData();
            menuGameView.showUserName(userInf.userName());
            menuGameView.showLevel(userInf.level());
            menuGameView.showExp(userInf.exp(), UserData.expCap(userInf.level()));
        }
        else{
            menuGameView.navigateTo(WellComeScreen.class);
        }
    }

    public void handleClassicClick(){
        //menuGameView.onClassicClicked();
        if(context instanceof MenuGameForm) {
            ((MenuGameForm) context).showPauseGameDialog();
        }
    }

    public void handleContinueClick(){
        //menuGameView.onContinueClicked();
        if(context instanceof MenuGameForm) {
            ((MenuGameForm) context).showLoseGameDialog();
        }
    }

    public void handleOnlineClick(){
        menuGameView.onContinueClicked();
    }

    public void handleSettingClick(){
        if(context instanceof MenuGameForm) {
            ((MenuGameForm) context).showSettingsDialog(true, true);
        }
    }

    public void handleExitClick(){
        if(context instanceof MenuGameForm) {
            ((MenuGameForm) context).showExitConfirmDialog();
        }
    }

    public void handleHelpContinueClick(){
        if(context instanceof MenuGameForm){
            ((MenuGameForm)context).showHelpDialog();
        }
    }

    public void showRankingList() {
        userData.getRankingList(new UserRepo.GetTopUserCallBack() {
            @Override
            public void onSuccess(List<userRanking> rankingList) {
                ((MenuGameForm)menuGameView).setupPlayerList(rankingList);
            }

            @Override
            public void onFailure(String err) {
                menuGameView.showMessage(err);
            }
        });
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
                        menuGameView.showAvatar(bitmap);
                    }
                    @Override
                    public void onFailure(String serverErr) {
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
