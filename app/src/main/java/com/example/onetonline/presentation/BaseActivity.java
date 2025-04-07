package com.example.onetonline.presentation;

import android.view.View;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kích hoạt chế độ immersive mode
        hideSystemUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Đảm bảo thanh điều hướng ẩn khi Activity được resume
        hideSystemUI();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Đảm bảo thanh điều hướng ẩn khi người dùng tương tác
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Ẩn thanh trạng thái và thanh điều hướng, kích hoạt chế độ immersive
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
