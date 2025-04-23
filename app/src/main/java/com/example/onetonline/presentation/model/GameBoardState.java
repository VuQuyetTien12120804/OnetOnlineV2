package com.example.onetonline.presentation.model;

import com.example.onetonline.utils.Constants;

import java.util.List;

public class GameBoardState {
    private int[][] matrix;
    private List<Pikachu> pikachuList;
    private Pikachu pikachuOne;
    private Pikachu pikachuTwo;
    private int score;
    private int time;
    private int searchCount;
    private int switchCount;
    private int level;

    public GameBoardState (int row, int col) {
        this.matrix = new int[row][col];
        this.level = 1;
        this.score = 0;
        this.time = Constants.TIME;
        this.searchCount = 7;
        this.switchCount = 7;
    }

    public void addPikachu(Pikachu clicked) {
        if (pikachuOne == null) {
            pikachuOne = clicked;
            pikachuOne.setSelected(true);
        } else if (pikachuTwo == null) {
            pikachuTwo = clicked;
            pikachuTwo.setSelected(true);
        }
    }
    public void clearPikachus() {
        if (pikachuOne != null) {
            pikachuOne.setSelected(false);
            pikachuOne = null;
        }
        if (pikachuTwo != null) {
            pikachuTwo.setSelected(false);
            pikachuTwo = null;
        }
    }

    public boolean hasTwoPikachus(){
        return pikachuOne != null && pikachuTwo != null;
    }

    public void removePikachus(){
        if (hasTwoPikachus()){
            matrix[pikachuOne.getxPoint()][pikachuOne.getyPoint()] = 0;
            matrix[pikachuTwo.getxPoint()][pikachuTwo.getyPoint()] = 0;
            pikachuOne.setxPoint(0); pikachuOne.setyPoint(0); pikachuOne.setImageID(0);
            pikachuTwo.setxPoint(0); pikachuTwo.setyPoint(0); pikachuTwo.setImageID(0);
            clearPikachus();
            score += Constants.plusScore;
        }
    }

    public boolean isClearMap(){
        int row = matrix.length;
        int col = matrix[0].length;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(matrix[i][j] != 0){
                    return false;
                }
            }
        }
        return true;
    }

    public List<Pikachu> getPikachuList() {
        return pikachuList;
    }

    public void setPikachuList(List<Pikachu> pikachuList) {
        this.pikachuList = pikachuList;
    }

    public Pikachu getPikachuOne() {
        return pikachuOne;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public Pikachu getPikachuTwo() {
        return pikachuTwo;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public int getSwitchCount() {
        return switchCount;
    }

    public int getLevel() {
        return level;
    }

    public void setPikachuOne(Pikachu pikachuOne) {
        this.pikachuOne = pikachuOne;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void setPikachuTwo(Pikachu pikachuTwo) {
        this.pikachuTwo = pikachuTwo;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }

    public void setSwitchCount(int switchCount) {
        this.switchCount = switchCount;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

