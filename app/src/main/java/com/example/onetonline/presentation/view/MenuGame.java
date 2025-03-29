package com.example.onetonline.presentation.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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
    private Button btnHelpClassic;
    private Button btnHelpContinue;
    private Button btnHelpRandom;

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


        btnClassic.setOnClickListener(v -> menuController.handleClassicButtonClick());
        btnContinue.setOnClickListener(v -> menuController.handleContinueButtonClick());
        btnOnline.setOnClickListener(v -> menuController.handleOnlineButtonClick());
        btnExit.setOnClickListener(v -> menuController.handleExitButtonClick());

        btnHelpContinue.setOnClickListener(v->menuController.handleHelpContinueButtonClick());
        btnHelpRandom.setOnClickListener(v->menuController.handleHelpContinueButtonClick());
        btnHelpClassic.setOnClickListener(v->menuController.handleHelpContinueButtonClick());

        btnMusic.setOnClickListener(v -> menuController.handleMusicButtonClick());
        btnAudio.setOnClickListener(v -> menuController.handleAudioButtonClick());

    }
    public void updateSelectedAction(String action){
        menuModel.setSelectedAction(action);

    }
}