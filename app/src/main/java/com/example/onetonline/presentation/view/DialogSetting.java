package com.example.onetonline.presentation.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.onetonlinev2.R;

public class DialogSetting {
    public static void showSettingsDialog(Context context, boolean isMusicOn, boolean isSoundClickOn, SettingsDialogListener listener) {
        // Inflate the dialog view
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_setting, null);

        // Create AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Set dialog animation and border (check existence)
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_border);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;

            // Set custom size
            dialog.getWindow().setLayout(
                    (int) (context.getResources().getDisplayMetrics().widthPixels * 0.25), // 85% width
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
        }

        // Bind views
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        Switch switchMusic = dialogView.findViewById(R.id.switchMusic);
        Switch switchSoundClick = dialogView.findViewById(R.id.switchSoundClick);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        // Set initial states
        tvTitle.setText("SETTING");
        switchMusic.setChecked(isMusicOn);
        switchSoundClick.setChecked(isSoundClickOn);

        // Handle save button
        btnSave.setOnClickListener(view -> {
            listener.onSettingsSaved(switchMusic.isChecked(), switchSoundClick.isChecked());
            dialog.dismiss();
        });

        // Show dialog
        dialog.show();
    }

    public interface SettingsDialogListener {
        void onSettingsSaved(boolean isMusicOn, boolean isSoundClickOn);
    }
}
