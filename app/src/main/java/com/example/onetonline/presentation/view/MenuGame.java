package com.example.onetonline.presentation.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onetonlinev2.R;
import com.example.onetonline.presentation.controller.*;

import java.io.IOException;

public class MenuGame extends AppCompatActivity implements MenuGameView{
    private MenuController menuController;
    private Button btnClassic, btnContinue, btnOnline, btnExit, btnHelpClassic, btnHelpContinue, btnHelpRandom;
//    private Button btnMusic, btnAudio;
    private ImageView ivAvatar;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    public void initWidgets(){
        btnClassic = findViewById(R.id.btnClassic);
        btnContinue = findViewById(R.id.btnContinue);
        btnOnline = findViewById(R.id.btnOnline);
        btnExit = findViewById(R.id.btnExitLoseGame);
//        btnMusic = findViewById(R.id.btnMusic);
//        btnAudio = findViewById(R.id.btnAudio);
        btnHelpClassic = findViewById(R.id.btnHelpClassic);
        btnHelpContinue = findViewById(R.id.btnHelpContinue);
        btnHelpRandom = findViewById(R.id.btnHelpRandom);
        ivAvatar = findViewById(R.id.ivAvatar);
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
                    menuController.handleSaveAvatar(bitmap); // Gửi đến Controller
                } catch (IOException e) {
                    showMessage("Error processing image: " + e.getMessage());
                }
            }
        });

        btnClassic.setOnClickListener(v -> handleClassicButtonClick());
        btnContinue.setOnClickListener(v -> handleContinueButtonClick());
        btnOnline.setOnClickListener(v -> handleOnlineButtonClick());
        btnExit.setOnClickListener(v -> handleExitButtonClick());
        ivAvatar.setOnClickListener(v -> menuController.handleChangeAvatar());
        btnHelpContinue.setOnClickListener(v->handleHelpContinueButtonClick());
        btnHelpRandom.setOnClickListener(v->handleHelpContinueButtonClick());
        btnHelpClassic.setOnClickListener(v->handleHelpContinueButtonClick());
//        btnMusic.setOnClickListener(v -> handleMusicButtonClick());
//        btnAudio.setOnClickListener(v -> handleAudioButtonClick());
    }


    public void updateSelectedAction(String action){

    }

    public void handleClassicButtonClick(){
        Dialog dialog = new Dialog(MenuGame.this);
        dialog.setContentView(R.layout.dialog_win_game);
        dialog.setCancelable(true); //cho phep dong bang nut ben ngoai

        Button btnNext = dialog.findViewById(R.id.btnNextWinGame);

        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuGame.this, MenuGame.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void handleContinueButtonClick(){
        //Tao dialog
        Dialog dialog = new Dialog(MenuGame.this);
        dialog.setContentView(R.layout.dialog_lose_game);
        dialog.setCancelable(true); //cho phep dong bang nut ben ngoai

        //gan cac nut cho dialog
        Button btnExitLoseGame = dialog.findViewById(R.id.btnExitLoseGame);

        // Áp dụng hiệu ứng cho dialog
        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
            // Xóa nền trắng của Dialog
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnExitLoseGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuGame.this, MenuGame.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void handleOnlineButtonClick(){

    }
    public void handleExitButtonClick() {
        Dialog dialog = new Dialog(MenuGame.this);
        dialog.setContentView(R.layout.dialog_exit_confirmation);
        dialog.setCancelable(false); // Không cho phép đóng bằng nút bên ngoài

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
    public void handleMusicButtonClick(){
        showCustomToast("Music button clicked");
    }
    public void handleAudioButtonClick(){
        showCustomToast("Audio button clicked");
    }
    public void handleHelpContinueButtonClick(){
        DialogHelper.showScrollableAlertDialog(MenuGame.this);
    }

    public void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private void showCustomToast(String message){
        // Inflate layout cuar custom toast
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView toastMessage = layout.findViewById(R.id.toast_message);
        toastMessage.setText(message);

        //tao toast
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
    public void navigateTo(Class<?> activityClass) {
        Intent i = new Intent(MenuGame.this, activityClass);
        startActivity(i);
        finish();
    }
}