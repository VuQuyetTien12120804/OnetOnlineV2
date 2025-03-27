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
    public void handleExitButtonClick() {
        // Tạo dialog
        Dialog dialog = new Dialog(view);
        dialog.setContentView(R.layout.dialog_exit_confirmation);
        dialog.setCancelable(false); // Không cho phép đóng bằng nút bên ngoài

        // Gắn các nút cho dialog
        Button btnYes = dialog.findViewById(R.id.btnYes);
        Button btnNo = dialog.findViewById(R.id.btnNo);

        // Áp dụng hiệu ứng cho dialog
        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
            // Xóa nền trắng của Dialog
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // Xử lý click "Có"
        btnYes.setOnClickListener(v -> {
            dialog.dismiss(); // Đóng dialog
            view.finish();
        });

        // Xử lý click "Không"
        btnNo.setOnClickListener(v -> dialog.dismiss()); // Đóng dialog

        // Hiển thị dialog
        dialog.show();
    }
    public void handleMusicButtonClick(){
        Toast.makeText(view, "Music button clicked", Toast.LENGTH_SHORT).show();
    }
    public void handleAudioButtonClick(){
        Toast.makeText(view, "Audio button clicked", Toast.LENGTH_SHORT).show();
    }

    public void handleHelpContinueButtonClick(){
        DialogHelper.showScrollableAlertDialog(view);
    }

}
