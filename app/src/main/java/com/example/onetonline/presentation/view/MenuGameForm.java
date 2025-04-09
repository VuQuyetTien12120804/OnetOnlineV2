package com.example.onetonline.presentation.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.onetonline.presentation.BaseActivity;
import com.example.onetonlinev2.R;
import com.example.onetonline.presentation.controller.*;

import java.io.IOException;

public class MenuGameForm extends BaseActivity implements MenuGameView {
    private MenuController menuController;
    private Button btnClassic, btnContinue, btnOnline, btnExit, btnHelpClassic, btnHelpContinue, btnHelpOnline, btnSetting;
    private ImageView ivAvatar;
    private TextView tvUserName, tvLevel, tvExp;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    public void initWidgets() {
        btnClassic = findViewById(R.id.btnClassic);
        btnContinue = findViewById(R.id.btnContinue);
        btnOnline = findViewById(R.id.btnOnline);
        btnExit = findViewById(R.id.btnExitLoseGame);
        btnHelpClassic = findViewById(R.id.btnHelpClassic);
        btnHelpContinue = findViewById(R.id.btnHelpContinue);
        btnHelpOnline = findViewById(R.id.btnHelpOnline);
        btnSetting = findViewById(R.id.btnSetting);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvUserName = findViewById(R.id.tvPlayerName);
        tvLevel = findViewById(R.id.tvStarCount);
        tvExp = findViewById(R.id.tvExp);
    }

    @Override
    protected void onStart() {
        super.onStart();
        menuController.loadAvatar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_game);

        menuController = new MenuController(this, this);
        initWidgets();

        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    menuController.handleSaveAvatar(bitmap);
                } catch (IOException e) {
                    showMessage("Lỗi khi xử lý ảnh: " + e.getMessage());
                }
            }
        });

        // Thêm kiểm tra null trước khi gọi setOnClickListener
        if (btnClassic != null) btnClassic.setOnClickListener(v -> menuController.handleClassicClick());
        if (btnContinue != null) btnContinue.setOnClickListener(v -> menuController.handleContinueClick());
        if (btnOnline != null) btnOnline.setOnClickListener(v -> menuController.handleOnlineClick());
        if (btnExit != null) btnExit.setOnClickListener(v -> menuController.handleExitClick());
        if (ivAvatar != null) ivAvatar.setOnClickListener(v -> menuController.handleChangeAvatar());
        if (btnHelpContinue != null) btnHelpContinue.setOnClickListener(v -> menuController.handleHelpContinueClick());
        if (btnHelpOnline != null) btnHelpOnline.setOnClickListener(v -> menuController.handleHelpContinueClick());
        if (btnHelpClassic != null) btnHelpClassic.setOnClickListener(v -> menuController.handleHelpContinueClick());
        if (btnSetting != null) btnSetting.setOnClickListener(v -> menuController.handleSettingClick());
    }

    @Override
    public void onClassicClicked() {
    }

    public void handleClassicButtonClick() {
        Dialog dialog = new Dialog(MenuGameForm.this);
        dialog.setContentView(R.layout.dialog_win_game);
        dialog.setCancelable(true);

        Button btnNext = dialog.findViewById(R.id.btnNextWinGame);

        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuGameForm.this, MenuGameForm.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void handleContinueButtonClick() {
        Dialog dialog = new Dialog(MenuGameForm.this);
        dialog.setContentView(R.layout.dialog_lose_game);
        dialog.setCancelable(true);

        Button btnExitLoseGame = dialog.findViewById(R.id.btnExitLoseGame);

        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnExitLoseGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuGameForm.this, MenuGameForm.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onContinueClicked() {
    }

    @Override
    public void onOnlineClicked() {
    }

    @Override
    public void onExitClicked() {
        Dialog dialog = new Dialog(MenuGameForm.this);

        dialog.setContentView(R.layout.dialog_exit_confirmation);
        dialog.setCancelable(false);

        Button btnYes = dialog.findViewById(R.id.btnYes);
        Button btnNo = dialog.findViewById(R.id.btnNo);

        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        btnNo.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void onHelpClassicClicked() {
        DialogHelper.showScrollableAlertDialog(MenuGameForm.this);
    }

    public void handleAudioButtonClick() {
        showCustomToast("Nút âm thanh được nhấn");
    }

    @Override
    public void onHelpContinueClicked() {
        DialogHelper.showScrollableAlertDialog(MenuGameForm.this);
    }

    @Override
    public void onSettingClicked() {
    }

    public void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView toastMessage = layout.findViewById(R.id.toast_message);
        toastMessage.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    @Override
    public void showAvatar(Bitmap bitmap) {
        ivAvatar.setImageBitmap(bitmap);
    }

    @Override
    public void showMessage(String message) {
        showCustomToast(message);
    }

    @Override
    public void showUserName(String userName) {
        tvUserName.setText(userName);
    }

    @Override
    public void showLevel(int level) {
        tvLevel.setText(String.valueOf(level));
    }

    @Override
    public void showExp(int exp, int expCap) {
        String experience = exp + "/" + expCap;
        tvExp.setText(experience);
    }

    @Override
    public String getUserName() {
        return tvUserName.getText().toString();
    }

    @Override
    public void navigateTo(Class<?> activityClass) {
        Intent i = new Intent(MenuGameForm.this, activityClass);
        startActivity(i);
        finish();
    }

    @Override
    public void showSettingsDialog(boolean isMusicOn, boolean isSoundClickOn) {
        DialogSetting.showSettingsDialog(this, isMusicOn, isSoundClickOn, (newMusicState, newSoundClickState) -> {
            menuController.saveSettings(newMusicState, newSoundClickState);
        });
    }

    @Override
    public void onSettingsSaved(boolean isMusicOn, boolean isSoundClickOn) {
        showMessage("Cài đặt đã lưu: Nhạc " + (isMusicOn ? "BẬT" : "TẮT") + ", Âm thanh nhấn " + (isSoundClickOn ? "BẬT" : "TẮT"));
    }
}