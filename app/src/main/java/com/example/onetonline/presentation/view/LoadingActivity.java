package com.example.onetonline.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onetonlinev2.R;

public class LoadingActivity extends AppCompatActivity { // Run multi thread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);

        // Move to MenuGame view in 3s
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, WellComeScreen.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); // Transition animation
            finish();
        }, 1000);
    }
}