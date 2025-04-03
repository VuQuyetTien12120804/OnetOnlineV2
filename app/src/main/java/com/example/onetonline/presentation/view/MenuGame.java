package com.example.onetonline.presentation.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.onetonlinev2.R;
import com.example.onetonline.presentation.controller.*;
import com.example.onetonline.presentation.model.*;

public class MenuGame extends AppCompatActivity {
    /**
     *
     */
    private MenuController menuController;
    private MenuModel menuModel;
    /**
     *
     */
    private static final int STORAGE_PERMISSION_CODE = 100;

    private Button btnClassic;
    private Button btnContinue;
    private Button btnOnline;
    private Button btnExit;
//    private Button btnMusic;
//    private Button btnAudio;
    private Button btnHelpClassic;
    private Button btnHelpContinue;
    private Button btnHelpRandom;

    private ImageView ivAvatar;

    // ActivityResultLauncher để xử lý kết quả chọn ảnh
    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_game);

        menuModel = new MenuModel();
        menuController = new MenuController(menuModel, this);

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

        // Khởi tạo ActivityResultLauncher để chọn ảnh
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                ivAvatar.setImageURI(imageUri); // Hiển thị ảnh đã chọn lên ImageView
            }
        });
        btnClassic.setOnClickListener(v -> handleClassicButtonClick());
        btnContinue.setOnClickListener(v -> handleContinueButtonClick());
        btnOnline.setOnClickListener(v -> handleOnlineButtonClick());
        btnExit.setOnClickListener(v -> handleExitButtonClick());
        // Xử lý sự kiện click trên avatar
        ivAvatar.setOnClickListener(v -> handleChangeAvatar());

        btnHelpContinue.setOnClickListener(v->handleHelpContinueButtonClick());
        btnHelpRandom.setOnClickListener(v->handleHelpContinueButtonClick());
        btnHelpClassic.setOnClickListener(v->handleHelpContinueButtonClick());

//        btnMusic.setOnClickListener(v -> handleMusicButtonClick());
//        btnAudio.setOnClickListener(v -> handleAudioButtonClick());

    }
    // Thay doi avartar trong thu muc
    public void handleChangeAvatar(){
        // Kiểm tra quyền trước khi mở thư viện ảnh
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MenuGame.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_CODE);
            } else {
                openImagePicker();
            }
        } else {
            openImagePicker();
        }
    }
    public void updateSelectedAction(String action){
        menuModel.setSelectedAction(action);

    }
    // Phương thức mở thư viện ảnh
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    // Xử lý kết quả yêu cầu quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Quyền truy cập bộ nhớ bị từ chối!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void handleHelpButtonClick(){
        Toast.makeText(MenuGame.this, "Help button clicked", Toast.LENGTH_SHORT).show();
    }
    // Xử lý classic button
    public void handleClassicButtonClick(){
        //Tao dialog
        Dialog dialog = new Dialog(MenuGame.this);
        dialog.setContentView(R.layout.dialog_win_game);
        dialog.setCancelable(true); //cho phep dong bang nut ben ngoai

        //gan cac nut cho dialog
        Button btnNext = dialog.findViewById(R.id.btnNextWinGame);

        // Áp dụng hiệu ứng cho dialog
        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
            // Xóa nền trắng của Dialog
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        //xu lu click next
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

        //xu lu click Try Again
        btnExitLoseGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuGame.this, MenuGame.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        //xu lu click Exit
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
        // Tạo dialog
        Dialog dialog = new Dialog(MenuGame.this);
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
            finish();
        });

        // Xử lý click "Không"
        btnNo.setOnClickListener(v -> dialog.dismiss()); // Đóng dialog

        // Hiển thị dialog
        dialog.show();
    }
    public void handleMusicButtonClick(){
        Toast.makeText(MenuGame.this, "Music button clicked", Toast.LENGTH_SHORT).show();
    }
    public void handleAudioButtonClick(){
        Toast.makeText(MenuGame.this, "Audio button clicked", Toast.LENGTH_SHORT).show();
    }
    public void handleHelpContinueButtonClick(){
        DialogHelper.showScrollableAlertDialog(MenuGame.this);
    }
}