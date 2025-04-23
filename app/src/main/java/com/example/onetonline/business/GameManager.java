package com.example.onetonline.business;

import android.content.Context;
import android.util.Log;

import com.example.onetonline.presentation.model.GameBoardState;
import com.example.onetonline.presentation.model.Pikachu;
import com.example.onetonline.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameManager {
    private Matrix matrix;
    private Context context;
    private GameBoardState gameBoardState;

    public GameManager(Context context) {
        this.context = context;
        gameBoardState = new GameBoardState(Constants.MAP_ROW + 2, Constants.MAP_COL + 2);
        matrix = new Matrix();
    }
    public List<Pikachu> makeMap(int[][] matrix){
        List<Pikachu> pikachuList = new ArrayList<>();
        for(int i = 1; i <= Constants.MAP_ROW; i++){
            for(int j = 1; j <= Constants.MAP_COL; j++){
                pikachuList.add(new Pikachu(i,j,matrix[i][j]));
            }
        }
        return pikachuList;
    }
    public List<Pikachu> createClassicBoard(){
        int[][] map = matrix.randomMatrix(gameBoardState.getMatrix());
        Log.d("GameManager: ",   " row: " + map.length + " col: " + map[0].length);
        Log.d("GameManager: ", Arrays.deepToString(map));
        gameBoardState.setMatrix(map);
        return makeMap(map);
    }
    public List<Pikachu> createContinueBoard(){
        int[][] savedMatrix = loadSavedGame();
        if (savedMatrix != null){
            gameBoardState.setMatrix(savedMatrix);
            return makeMap(savedMatrix);
        }
        else{
            return createClassicBoard();
        }
    }

    public boolean checkAndRemove(){
        if (gameBoardState.hasTwoPikachus()) {
            Pikachu one = gameBoardState.getPikachuOne();
            Pikachu two = gameBoardState.getPikachuTwo();
            if (matrix.algorithm(gameBoardState.getMatrix(), one, two) && !one.equals(two)) {
                gameBoardState.removePikachus();
                return true;
            }
            gameBoardState.clearPikachus();
        }
        return false;
    }

    public void updatePikachuMap (int[][] map, List<Pikachu> pikachuListNew){
        pikachuListNew.clear();
        int row = map.length;
        int col = map[0].length;
        for (int i = 1; i <= row - 2; i++){
            for (int j = 1; j <= col - 2; j++){
                if (map[i][j] != 0){
                    pikachuListNew.add(new Pikachu(i,j,map[i][j]));
                }
                else{
                    pikachuListNew.add(new Pikachu(i,j,0));
                }
            }
        }
    }
    private int[][] loadSavedGame(){
        int[][] board = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 27, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 22, 21, 26, 0, 0, 33, 15, 34, 28, 18, 0, 0, 0, 0, 0, 26, 0},
                {0, 10, 4, 19, 0, 36, 29, 30, 21, 29, 32, 0, 0, 0, 0, 29, 30, 0},
                {0, 34, 17, 5, 0, 6, 15, 4, 17, 18, 24, 36, 4, 0, 0, 30, 13, 0},
                {0, 16, 11, 28, 4, 31, 36, 15, 7, 14, 4, 25, 14, 9, 14, 24, 29, 0},
                {0, 19, 35, 20, 19, 30, 21, 30, 36, 11, 31, 1, 30, 25, 19, 21, 15, 0},
                {0, 3, 28, 11, 12, 4, 18, 16, 3, 11, 7, 19, 25, 14, 27, 2, 2, 0},
                {0, 14, 9, 33, 24, 1, 27, 25, 22, 18, 6, 28, 12, 32, 16, 30, 5, 0},
                {0, 30, 24, 10, 9, 9, 14, 35, 25, 13, 20, 25, 27, 36, 36, 16, 19, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        return board;
    }

    public GameBoardState getGameBoardState() {
        return gameBoardState;
    }
    public Matrix getMatrix(){return this.matrix;}
}
