package com.example.onetonline.presentation.view;

import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;

import com.example.onetonline.presentation.BaseActivity;
import com.example.onetonline.presentation.model.GameBoardState;
import com.example.onetonlinev2.R;
import com.example.onetonline.presentation.controller.*;
import com.example.onetonline.presentation.model.*;


public class GamePlay extends BaseActivity {
    private GamePlayController gamePlayController;
    private GameBoardState gameBoardState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_play);
    }
}
