package com.example.onetonline.presentation.view;

import com.example.onetonline.presentation.model.GameBoardState;

public interface GamePlayListener {
    void setUpBoard(GameBoardState state);
    void setScore(int score);
    void setLevel(int level);
    void onButtonXClick();
    void onButtonSearchClick(GameBoardState state);
    void txtSearchListener(GameBoardState state);
    void onButtonSwitchClick(GameBoardState state);
    void txtSwitchListener(GameBoardState state);
}
