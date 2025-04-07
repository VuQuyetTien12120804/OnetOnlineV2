package com.example.onetonline.presentation.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.onetonlinev2.R;

public class DialogSetting {
    public static void showSettingsDialog(Context context, boolean isMusicOn, boolean isSoundClickOn, SettingsDialogListener listener) {
        // Tạo View từ layout
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_setting, null); // Sửa tên layout

        // Khởi tạo AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        // Tạo AlertDialog
        AlertDialog dialog = builder.create();

        // Áp dụng hiệu ứng cho dialog
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;

        // Áp dụng viền cho AlertDialog
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_border);

        // Ánh xạ các thành phần
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle); // Sửa ID
        Switch switchMusic = dialogView.findViewById(R.id.switchMusic);
        Switch switchSoundClick = dialogView.findViewById(R.id.switchSoundClick);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        // Cài đặt nội dung
        tvTitle.setText("Cài đặt");
        switchMusic.setChecked(isMusicOn);
        switchSoundClick.setChecked(isSoundClickOn);

        // Xử lý nút Save
        btnSave.setOnClickListener(view -> {
            boolean newMusicState = switchMusic.isChecked();
            boolean newSoundClickState = switchSoundClick.isChecked();
            listener.onSettingsSaved(newMusicState, newSoundClickState);
            dialog.dismiss();
        });

        // Hiển thị AlertDialog
        dialog.show();
    }

    // Callback interface for settings dialog
    public interface SettingsDialogListener {
        void onSettingsSaved(boolean isMusicOn, boolean isSoundClickOn);
    }
}