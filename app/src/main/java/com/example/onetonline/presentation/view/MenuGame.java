package com.example.onetonline.presentation.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
    private Button btnMusic;
    private Button btnAudio;
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
        btnMusic = findViewById(R.id.btnMusic);
        btnAudio = findViewById(R.id.btnAudio);
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


        btnClassic.setOnClickListener(v -> menuController.handleClassicButtonClick());
        btnContinue.setOnClickListener(v -> menuController.handleContinueButtonClick());
        btnOnline.setOnClickListener(v -> menuController.handleOnlineButtonClick());
        btnExit.setOnClickListener(v -> menuController.handleExitButtonClick());
        // Xử lý sự kiện click trên avatar
        ivAvatar.setOnClickListener(v -> handleChangeAvatar());

        btnHelpContinue.setOnClickListener(v->menuController.handleHelpContinueButtonClick());
        btnHelpRandom.setOnClickListener(v->menuController.handleHelpContinueButtonClick());
        btnHelpClassic.setOnClickListener(v->menuController.handleHelpContinueButtonClick());

        btnMusic.setOnClickListener(v -> menuController.handleMusicButtonClick());
        btnAudio.setOnClickListener(v -> menuController.handleAudioButtonClick());

    }

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
}