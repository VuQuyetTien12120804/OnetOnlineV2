package com.example.onetonline.presentation.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.onetonline.business.GameManager;
import com.example.onetonline.presentation.model.Pikachu;
import com.example.onetonline.presentation.view.GamePlayListener;
import com.example.onetonline.presentation.view.GamePlay;
import com.example.onetonline.utils.Constants;

import java.util.List;

public class GamePlayController {
    private GameManager gameManager;
    private GamePlayListener view;

    public GamePlayController(GamePlayListener view){
        this.view = view;
        gameManager = new GameManager((Context) view);
    }
    public void initGame(boolean isClassic, boolean isOnline){
        List<Pikachu> pikachuList;
        if (isClassic){
            pikachuList = gameManager.createClassicBoard();
            gameManager.getGameBoardState().setPikachuList(pikachuList);
        }
        else{
            pikachuList = gameManager.createContinueBoard();
            gameManager.getGameBoardState().setPikachuList(pikachuList);
        }
        Log.d("GamePlayController: ", pikachuList.toString() + " " + pikachuList.size());
        view.setUpBoard(gameManager.getGameBoardState());
        if (!gameManager.getMatrix().canPlay(gameManager.getGameBoardState().getMatrix())){
            shuffleMap();
        }
    }
    public void handlePikachuClick(int position){
        Pikachu clicked = gameManager.getGameBoardState().getPikachuList().get(position);
        gameManager.getGameBoardState().addPikachu(clicked);
        ((GamePlay) view).getAdapter().notifyItemChanged(position);
        Log.d("GamePlayController: ", "CLicked pikachu at (" + clicked.getxPoint() + ", " + clicked.getyPoint() + ")");
        if (gameManager.getGameBoardState().hasTwoPikachus()){
            int x1 = gameManager.getGameBoardState().getPikachuOne().getxPoint();
            int y1 = gameManager.getGameBoardState().getPikachuOne().getyPoint();
            int x2 = gameManager.getGameBoardState().getPikachuTwo().getxPoint();
            int y2 = gameManager.getGameBoardState().getPikachuTwo().getyPoint();
            if (gameManager.checkAndRemove()){
                view.setScore(gameManager.getGameBoardState().getScore());
                Log.d("controller remove ", "(" + x1 + ", " + y1 + ") and (" + x2 + ", " + y2 +")");
            }
            ((GamePlay) view).getAdapter().notifyItemChanged(Constants.position(x1,y1));
            ((GamePlay) view).getAdapter().notifyItemChanged(Constants.position(x2,y2));
            nextLevel();
            if (!gameManager.getMatrix().canPlay(gameManager.getGameBoardState().getMatrix())){
                shuffleMap();
            }

        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void shuffleMap(){
        gameManager.getMatrix().shuffleMatrix(gameManager.getGameBoardState().getMatrix());
        gameManager.updatePikachuMap(gameManager.getGameBoardState().getMatrix(), gameManager.getGameBoardState().getPikachuList());
        ((GamePlay) view).getAdapter().notifyDataSetChanged();
        gameManager.getGameBoardState().setSwitchCount(gameManager.getGameBoardState().getSwitchCount() - 1);
        view.onButtonSwitchClick(gameManager.getGameBoardState());
    }

    public void handleSearchClick(){
        view.onButtonSearchClick(gameManager.getGameBoardState());
        Pikachu[] temp = gameManager.getMatrix().findPikachu(gameManager.getGameBoardState().getMatrix());
        Pikachu pikachu1 = temp[0];
        Pikachu pikachu2 = temp[1];
        int pos1 = Constants.position(pikachu1.getxPoint(), pikachu1.getyPoint());
        int pos2 = Constants.position(pikachu2.getxPoint(), pikachu2.getyPoint());
        gameManager.getGameBoardState().getPikachuList().get(pos1).setSelected(true);
        gameManager.getGameBoardState().getPikachuList().get(pos2).setSelected(true);
        ((GamePlay) view).getAdapter().notifyItemChanged(pos1);
        ((GamePlay) view).getAdapter().notifyItemChanged(pos2);

        gameManager.getGameBoardState().setSearchCount(gameManager.getGameBoardState().getSearchCount() - 1);
        view.onButtonSearchClick(gameManager.getGameBoardState());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void nextLevel(){
        if (gameManager.getGameBoardState().isClearMap()){
            int level = gameManager.getGameBoardState().getLevel();
            gameManager.getGameBoardState().setLevel(level + 1);
            int[][] map = gameManager.getMatrix().randomMatrix(gameManager.getGameBoardState().getMatrix());
            if (level % 3 == 1){
                gameManager.getGameBoardState().setMatrix(map);
            } else if (level % 3 == 2){
                gameManager.getGameBoardState().setMatrix(gameManager.getMatrix().level2(map));
            }
            else if (level % 3 == 0){
                gameManager.getGameBoardState().setMatrix(gameManager.getMatrix().level3(map));
            }
            gameManager.updatePikachuMap(gameManager.getGameBoardState().getMatrix(), gameManager.getGameBoardState().getPikachuList());
            ((GamePlay) view).getAdapter().notifyDataSetChanged();
            gameManager.getGameBoardState().setSwitchCount(gameManager.getGameBoardState().getSwitchCount() + 1);
            System.out.println("Controller: " + gameManager.getGameBoardState().getSwitchCount());
            view.onButtonSwitchClick(gameManager.getGameBoardState());
        }
    }
    public void handleXClick(){
        view.onButtonXClick();
    }

    public void handleTxTSearch(){
        view.txtSearchListener(gameManager.getGameBoardState());
    }

    public void handleTxtSwitch(){
        view.txtSwitchListener(gameManager.getGameBoardState());
    }

    public void handleTxtLevel(){

    }
}
