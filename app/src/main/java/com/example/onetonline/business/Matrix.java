package com.example.onetonline.business;

import java.util.HashMap;
import java.util.Random;

import com.example.onetonline.presentation.model.Pikachu;
import com.example.onetonlinev2.R;

public class Matrix {
    private int[][] matrix;
    private int row; // map_row + 2;
    private int col; // map_col + 2;
    private int value;
    private static final int CONST_VALUE = 0;
    private HashMap<Integer, Integer> countMap;

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public HashMap<Integer, Integer> getCountMap() {
        return countMap;
    }

    public void setCountMap(HashMap<Integer, Integer> countMap) {
        this.countMap = countMap;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Initialize matrix
     */

    public void randomMatrix(){
        matrix = new int[row][col];
        this.countMap  = new HashMap<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = CONST_VALUE;
            }
        }
        Random random = new Random();
        for (int i = 1; i <= row - 2; i++) {
            for (int j = 1; j <= col - 2; j++) {
                //matrix[i][j] = random.nextInt(getResources().getInteger(R.integer.pikachu_number)) + 1;
            }


        }
    }
    /**
     * Set value for Pikachu(x,y)
     */
    public void setXY(Pikachu pikachu, int value){
        this.matrix[pikachu.getxPoint()][pikachu.getyPoint()] = value;
    }

}
