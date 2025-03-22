package com.example.onetonline.presentation.controller;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.onetonline.presentation.model.MenuModel;
import com.example.onetonline.presentation.view.*;
import com.example.onetonlinev2.R;

public class MenuController {
    private MenuModel menuModel;
    private MenuGame view;

    public MenuController(MenuModel menuModel, MenuGame view) {
        this.menuModel = menuModel;
        this.view = view;

    }
    public void handleHelpButtonClick(){
        Toast.makeText(view, "Help button clicked", Toast.LENGTH_SHORT).show();
    }
    public void handleClassicButtonClick(){

    }
    public void handleContinueButtonClick(){

    }
    public void handleOnlineButtonClick(){

    }
    public void handleExitButtonClick(){
        //Tao dialog
        Dialog dialog = new Dialog(view);
        dialog.setContentView(R.layout.dialog_exit_confirmation);
        dialog.setCancelable(false); // khong cho phep dong bang nut ben ngoai

        //Gan cac nut cho dialog
        Button btnYes = dialog.findViewById(R.id.btnYes);
        Button btnNo = dialog.findViewById(R.id.btnNo);

        // Xóa nền trắng của Dialog
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        //Xu ly click Co
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // đóng dialog
                view.finish(); //Két thức Activity Hiện tại
            }
        });

        //Xu ly click Khong
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // đóng dialog
            }
        });

        //Hien thi dialog
        dialog.show();
    }
    public void handleMusicButtonClick(){
        Toast.makeText(view, "Music button clicked", Toast.LENGTH_SHORT).show();
    }
    public void handleAudioButtonClick(){
        Toast.makeText(view, "Audio button clicked", Toast.LENGTH_SHORT).show();
    }

}
