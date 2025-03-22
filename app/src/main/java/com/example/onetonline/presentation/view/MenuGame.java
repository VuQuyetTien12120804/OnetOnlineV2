package com.example.onetonline.presentation.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

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
    private Button btnClassic;
    private Button btnContinue;
    private Button btnOnline;
    private Button btnExit;
    private Button btnMusic;
    private Button btnAudio;
    private Button btnHelp1;
    private Button btnHelp2;
    private Button btnHelp3;

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
        btnExit = findViewById(R.id.btnExit);
        btnMusic = findViewById(R.id.btnMusic);
        btnAudio = findViewById(R.id.btnAudio);
        btnHelp1 = findViewById(R.id.btnHelp1);
        btnHelp2 = findViewById(R.id.btnHelp2);
        btnHelp3 = findViewById(R.id.btnHelp3);

        btnHelp1.setOnClickListener(v -> menuController.handleHelpButtonClick());
        btnHelp2.setOnClickListener(v -> menuController.handleHelpButtonClick());
        btnHelp3.setOnClickListener(v -> menuController.handleHelpButtonClick());

        btnClassic.setOnClickListener(v -> menuController.handleClassicButtonClick());
        btnContinue.setOnClickListener(v -> menuController.handleContinueButtonClick());
        btnOnline.setOnClickListener(v -> menuController.handleOnlineButtonClick());
        btnExit.setOnClickListener(v -> menuController.handleExitButtonClick());

        btnMusic.setOnClickListener(v -> menuController.handleMusicButtonClick());
        btnAudio.setOnClickListener(v -> menuController.handleAudioButtonClick());

    }
    public void updateSelectedAction(String action){
        menuModel.setSelectedAction(action);

    }
}