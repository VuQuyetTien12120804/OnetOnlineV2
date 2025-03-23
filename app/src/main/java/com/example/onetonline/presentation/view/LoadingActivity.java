package com.example.onetonline.presentation.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.onetonlinev2.R;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvProgressPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        progressBar = findViewById(R.id.progressBar);
        tvProgressPercent = findViewById(R.id.tvProgressPercent);
        //ImageView imgLogo = findViewById(R.id.imgLogo);

        // Animate Progress
        animateProgress();

        // Move to next screen after 5 seconds
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LoadingActivity.this, WellComeScreen.class);
            startActivity(intent);
            finish();
        }, 5000);
    }

    private void animateProgress() {
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        progressAnimator.setDuration(5000); // 5 giây
        progressAnimator.addUpdateListener(animation -> {
            int progress = (int) animation.getAnimatedValue();
            tvProgressPercent.setText(progress + "%");
        });
        progressAnimator.start();
    }

}
