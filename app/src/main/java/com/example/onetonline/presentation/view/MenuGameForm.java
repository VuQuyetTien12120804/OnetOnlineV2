package com.example.onetonline.presentation.view;

import static com.example.onetonline.utils.Constants.SYNC_SUCCESS_ACTION;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.onetonline.broadcast.MusicGameService;
import com.example.onetonline.broadcast.SyncService;
import com.example.onetonline.data.User;
import com.example.onetonline.data.userRanking;
import com.example.onetonline.presentation.BaseActivity;
import com.example.onetonline.presentation.controller.MenuController;
import com.example.onetonlinev2.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuGameForm extends BaseActivity implements MenuGameView {
    private MenuController menuController;
    private Button btnClassic, btnContinue, btnOnline, btnExit, btnHelpClassic, btnHelpContinue, btnHelpOnline, btnSetting;
    private ImageView ivAvatar;
    private TextView tvUserName, tvLevel, tvExp;
    private TabHost tabHost;
    private boolean isMusicOn = true, isSoundClickOn = true;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private BroadcastReceiver syncReceiver;

    public void initWidgets() {
        btnClassic = findViewById(R.id.btnClassic);
        btnContinue = findViewById(R.id.btnContinue);
        btnOnline = findViewById(R.id.btnOnline);
        btnExit = findViewById(R.id.btnExit);
        btnHelpClassic = findViewById(R.id.btnHelpClassic);
        btnHelpContinue = findViewById(R.id.btnHelpContinue);
        btnHelpOnline = findViewById(R.id.btnHelpOnline);
        btnSetting = findViewById(R.id.btnSetting);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvUserName = findViewById(R.id.tvPlayerName);
        tvLevel = findViewById(R.id.tvStarCount);
        tvExp = findViewById(R.id.tvExp);
        tabHost = findViewById(R.id.mytab);
    }

    private void setupTabs() {
        tabHost.setup();

        TabHost.TabSpec todayTab = tabHost.newTabSpec("Best Score");
        todayTab.setContent(R.id.tab1);
        todayTab.setIndicator("Best Score");
        tabHost.addTab(todayTab);
    }

    public void setupPlayerList(List<userRanking> rankingList) {
        // Tạo adapter và gán vào ListView
        UserAdapter adapter = new UserAdapter(this, rankingList);
        ListView playerListView = findViewById(R.id.playerListView);
        playerListView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        menuController.loadUserData();
        menuController.loadAvatar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_game);

        menuController = new MenuController(this, this);
        initWidgets();

        //      start service to sync user data with server
        Intent serviceIntent = new Intent(this, SyncService.class);
        startService(serviceIntent);

        // Đăng ký receiver để nhận broadcast đồng bộ
        syncReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                menuController.loadUserData();
                menuController.loadAvatar();
                menuController.showRankingList(); // Làm mới danh sách User
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(syncReceiver, new IntentFilter(SYNC_SUCCESS_ACTION));

        setupTabs();
        menuController.showRankingList();

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

        btnClassic.setOnClickListener(v -> menuController.handleClassicClick());
        btnContinue.setOnClickListener(v -> menuController.handleContinueClick());
        btnOnline.setOnClickListener(v -> menuController.handleOnlineClick(tvUserName.getText().toString()));
        btnExit.setOnClickListener(v -> menuController.handleExitClick());
        ivAvatar.setOnClickListener(v -> menuController.handleChangeAvatar());
        btnHelpContinue.setOnClickListener(v -> menuController.handleHelpContinueClick());
        btnHelpOnline.setOnClickListener(v -> menuController.handleHelpContinueClick());
        btnHelpClassic.setOnClickListener(v -> menuController.handleHelpContinueClick());
        btnSetting.setOnClickListener(v -> menuController.handleSettingClick());
    }

    @Override
    public void onClassicClicked() {
    }

    @Override
    public void onContinueClicked() {
    }

    @Override
    public void onOnlineClicked() {
    }

    @Override
    public void onExitClicked() {
    }

    @Override
    public void onHelpClassicClicked() {
    }

    @Override
    public void onHelpContinueClicked() {
    }

    @Override
    public void onSettingClicked() {
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
    public void navigateTo(Class<?> avtivityClass) {
        Intent intent = new Intent(this, avtivityClass);
        startActivity(intent);
        finish();
    }

    @Override
    public String getUserName() {
        return tvUserName.getText().toString();
    }

    @Override
    public void onBackPressed() {
        // Điều hướng về màn hình WellComeScreen
        Intent intent = new Intent(this, WellComeScreen.class);
        startActivity(intent);
        finish(); // Kết thúc Activity hiện tại
    }

    //Xử lý nút setting
    @Override
    public void showSettingsDialog(boolean isMusicOn, boolean isSoundClickOn) {
        //tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //load giao diện lại từ file xml
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_setting, null);

        //gán giao diện vào dialog
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        //xóa nền
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //xử lý cho switch
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        Switch switchMusic = dialogView.findViewById(R.id.switchMusic);
        Switch switchSoundClick = dialogView.findViewById(R.id.switchSoundClick);

        // Lấy trạng thái từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        boolean musicState = sharedPreferences.getBoolean("MUSIC_STATE", isMusicOn);
        boolean soundState = sharedPreferences.getBoolean("SOUND_STATE", isSoundClickOn);

        // Hiển thị trạng thái hiện tại
        switchMusic.setChecked(musicState);
        switchSoundClick.setChecked(soundState);

        AppCompatButton btnLogOut = dialogView.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(v -> menuController.handleLogOutClick());

        //Xử ly nut save
        AppCompatButton btnSave = dialogView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v->{
            // Lưu trạng thái mới vào SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("MUSIC_STATE", switchMusic.isChecked());
            editor.putBoolean("SOUND_STATE", switchSoundClick.isChecked());
            editor.apply();

            //xử lý phát nhạc
            if(switchMusic.isChecked()){
                startService(new Intent(MenuGameForm.this, MusicGameService.class));
            }else{
                stopService(new Intent(MenuGameForm.this, MusicGameService.class));
            }

            // Đóng dialog
            dialog.dismiss();
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
        });
        //show
        dialog.show();
    }

    @Override
    public void onSettingsSaved(boolean isMusicOn, boolean isSoundClickOn) {
        showMessage("Cài đặt đã lưu: Nhạc " + (isMusicOn ? "BẬT" : "TẮT") + ", Âm thanh nhấn " + (isSoundClickOn ? "BẬT" : "TẮT"));
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
    //Xử lý click nút Exit
    public void showExitConfirmDialog(){
        //tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //load giao diện lại từ file xml
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_exit_confirmation, null);

        //gán giao diện vào dialog
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        //xóa nền
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvExitMessage = dialogView.findViewById(R.id.tvExitMessage);
        AppCompatButton btnYesConfirmExitGame = dialogView.findViewById(R.id.btnYesConfirmExitGame);
        AppCompatButton btnNoConfirmExitGame = dialogView.findViewById(R.id.btnNoConfirmExitGame);

        //xử lý cho nut yes
        btnYesConfirmExitGame.setOnClickListener(v->{
            dialog.dismiss(); //đóng dialog
            finish(); //đóng activity hiện tại
        });

        //xử lý cho nut no
        btnNoConfirmExitGame.setOnClickListener(v->{
            dialog.dismiss(); //đóng dialog
        });

        //Hiển thị dialog
        dialog.show();
    }

    //Xử lý click 3 nút Help
    public void showHelpDialog(){
        //tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Load giao dien tu file xml
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_helper_scrollable, null);
        //gan giao dien vao dialog
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        //xóa nền
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //xử lý cho nut close
        Button btnClose = dialogView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v->{
            dialog.dismiss();
        });
        //Hiển thị dialog
        dialog.show();
    }
    public void showWinGameDialog(){
        //tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Load giao dien tu file xml
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_win_game, null);
        //gan giao dien vao dialog
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        //xóa nền
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //xử lý cho nut close
        Button btnNextWinGame = dialogView.findViewById(R.id.btnNextWinGame);
        btnNextWinGame.setOnClickListener(v->{
            dialog.dismiss();
        });
        //Hiển thị dialog
        dialog.show();
    }
    public void showLoseGameDialog(){
        //tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Load giao dien tu file xml
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_lose_game, null);
        //gan giao dien vao dialog
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        //xóa nền
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //xử lý cho nut close
        Button btnExitLoseGame = dialogView.findViewById(R.id.btnExitLoseGame);
        btnExitLoseGame.setOnClickListener(v->{
            dialog.dismiss();
        });
        //Hiển thị dialog
        dialog.show();
    }
    public void showPauseGameDialog(){
        //tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Load giao dien tu file xml
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_pause, null);
        //gan giao dien vao dialog
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        //xóa nền
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //xử lý cho nut close
        Button btnExitPauseGame = dialogView.findViewById(R.id.btnExitPauseGame);
        btnExitPauseGame.setOnClickListener(v->{
            dialog.dismiss();
        });
        //Hiển thị dialog
        dialog.show();
    }
}