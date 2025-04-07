package com.example.onetonline.business;

import static com.example.onetonline.utils.Constants.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.example.onetonline.presentation.model.Pikachu;
import com.example.onetonline.utils.*;
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
    public Matrix(int [][] matrix, int row, int col) {
        this.matrix = matrix;
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
     * Set value for Pikachu(x,y)
     */
    public void setXY(Pikachu pikachu, int value) {
        this.matrix[pikachu.getxPoint()][pikachu.getyPoint()] = value;
    }

    /**
     * Get value for Pikachu(x,y)
     */
    public int getXY(Pikachu pikachu) {
        return this.matrix[pikachu.getxPoint()][pikachu.getyPoint()];
    }

    /**
     * Initialize matrix
     */

    public void randomMatrix() {
        this.matrix = new int[row][col];
        this.countMap = new HashMap<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = CONST_VALUE;
            }
        }
        Random random = new Random();
        for (int i = 1; i <= row - 2; i++) {
            for (int j = 1; j <= col - 2; j++) {
                matrix[i][j] = random.nextInt(PIKACHU_NUMBER) + 1;
                if (countMap.containsKey(matrix[i][j])) {
                    countMap.put(matrix[i][j], countMap.get(matrix[i][j]) + 1);
                } else {
                    countMap.put(matrix[i][j], 1);
                }

            }
        }
        /**
         * Reshape matrix Pikachu
         */
        for (int pikachu : countMap.keySet()) {
            int count = countMap.get(pikachu);
            if (count % 2 != 0) {
                int newPikachu = 0;
                boolean check = false;
                while (!check) {
                    newPikachu = random.nextInt(Constants.PIKACHU_NUMBER) + 1;
                    if (newPikachu != pikachu) {
                        if (countMap.containsKey(newPikachu)) {
                            if (countMap.get(newPikachu) % 2 != 0) {
                                System.out.println(pikachu + " " + count + " " + newPikachu + " " + countMap.get(newPikachu));
                                countMap.put(newPikachu, countMap.get(newPikachu) + 1);
                                check = true;
                            }
                        }
                    }
                }
                outerloop:
                for (int i = 1; i <= row - 1; i++) {
                    for (int j = 1; j <= col - 1; j++) {
                        if (matrix[i][j] == pikachu) {
                            matrix[i][j] = newPikachu;
                            countMap.put(pikachu, countMap.get(pikachu) - 1);
                            break outerloop;
                        }
                    }
                }
            }
        }
    }

    /**
     * Check Pikachu matches in the horizontal direction.
     */
    private boolean checkLineX(int y1, int y2, int x) {
        int min = Math.min(y1, y2);
        int max = Math.max(y1, y2);
        for (int y = min + 1; y < max; y++) {
            if (matrix[x][y] != CONST_VALUE)
                return false;
        }
        //System.out.println("checkX");
        return true;
    }

    /**
     * Check Pikachu matches in the vertical direction.
     */
    private boolean checkLineY(int x1, int x2, int y) {
        int min = Math.min(x1, x2);
        int max = Math.max(x1, x2);
        for (int x = min + 1; x < max; x++) {
            if (matrix[x][y] != CONST_VALUE)
                return false;
        }
        //System.out.println("checkY");
        return true;
    }

    /**
     * Check Pikachu matches in the rectangle direction.
     */
    private boolean checkRectX(Pikachu one, Pikachu two) {
        Pikachu pMinY = one;
        Pikachu pMaxY = two;
        if (one.getyPoint() > two.getyPoint()) {
            pMaxY = one;
            pMinY = two;
        }
        for (int y = pMinY.getyPoint(); y <= pMaxY.getyPoint(); y++) {
            if (y > pMinY.getyPoint() && matrix[pMinY.getxPoint()][y] != CONST_VALUE) {
                return false;
            }
            if ((matrix[pMaxY.getxPoint()][y] == CONST_VALUE || y == pMaxY.getyPoint())
                    && checkLineY(pMinY.getxPoint(), pMaxY.getxPoint(), y)
                    && checkLineX(y, pMaxY.getyPoint(), pMaxY.getxPoint())) {
//                System.out.println("Rect x");
//                System.out.println("(" + pMinY.getxPoint() + "," + pMinY.getyPoint() + ") -> ("
//                        + pMinY.getxPoint() + "," + y + ") -> (" + pMaxY.getxPoint() + "," + y
//                        + ") -> (" + pMaxY.getxPoint() + "," + pMaxY.getyPoint() + ")");
                return true;
            }
        }
        return false;
    }

    /**
     * Check Pikachu matches in the rectangle direction.
     */
    private boolean checkRectY(Pikachu one, Pikachu two) {
        Pikachu pMinX = one;
        Pikachu pMaxX = two;
        if (one.getxPoint() > two.getxPoint()) {
            pMaxX = one;
            pMinX = two;
        }
        for (int x = pMinX.getxPoint(); x <= pMaxX.getxPoint(); x++) {
            if (x > pMinX.getxPoint() && matrix[x][pMinX.getyPoint()] != CONST_VALUE)
                return false;
            if ((matrix[x][pMaxX.getyPoint()] == CONST_VALUE || x == pMaxX.getxPoint())
                    && checkLineX(pMinX.getyPoint(), pMaxX.getyPoint(), x)
                    && checkLineY(x, pMaxX.getxPoint(), pMaxX.getyPoint())) {
                //  System.out.println("Rect y");
                // System.out.println("(" + pMinX.getxPoint() + "," + pMinX.getyPoint() + ") -> (" + x
                //     + "," + pMinX.getyPoint() + ") -> (" + x + "," + pMaxX.getyPoint()
                //     + ") -> (" + pMaxX.getxPoint() + "," + pMaxX.getyPoint() + ")");
                return true;
            }
        }
        return false;
    }

    /**
     * Check Pikachu matches in the more line direction.
     */
    private boolean checkMoreLineX(Pikachu one, Pikachu two, int type) {
        Pikachu pMinY = one;
        Pikachu pMaxY = two;
        if (one.getyPoint() > two.getyPoint()) {
            pMaxY = one;
            pMinY = two;
        }
        int y = pMaxY.getyPoint() + type;
        int row = pMinY.getxPoint();
        int colFinish = pMaxY.getyPoint();
        if (type == -1) {
            y = pMinY.getyPoint() + type;
            row = pMaxY.getxPoint();
            colFinish = pMinY.getyPoint();
        }
        if ((matrix[row][colFinish] == CONST_VALUE || pMinY.getyPoint() == pMaxY.getyPoint())
                && checkLineX(pMinY.getyPoint(), pMaxY.getyPoint(), row)) {
            while (this.matrix[pMinY.getxPoint()][y] == CONST_VALUE
                    && this.matrix[pMaxY.getxPoint()][y] == CONST_VALUE) {
                if (checkLineY(pMinY.getxPoint(), pMaxY.getxPoint(), y)) {
                    //  System.out.println("checkMoreLineX" + " type: " + type);
                    return true;
                }
                y += type;
            }
        }
        return false;
    }

    /**
     * Check Pikachu matches in the more line direction.
     */
    private boolean checkMoreLineY(Pikachu one, Pikachu two, int type) {
        Pikachu pMinX = one;
        Pikachu pMaxX = two;
        if (one.getxPoint() > two.getxPoint()) {
            pMaxX = one;
            pMinX = two;
        }
        int x = pMaxX.getxPoint() + type;
        int col = pMinX.getyPoint();
        int rowFinish = pMaxX.getxPoint();
        if (type == -1) {
            x = pMinX.getxPoint() + type;
            col = pMaxX.getyPoint();
            rowFinish = pMinX.getxPoint();
        }
        if ((this.matrix[rowFinish][col] == CONST_VALUE || pMinX.getxPoint() == pMaxX.getxPoint())
                && checkLineY(pMinX.getxPoint(), pMaxX.getxPoint(), col)) {
            while (this.matrix[x][pMinX.getyPoint()] == CONST_VALUE
                    && this.matrix[x][pMaxX.getyPoint()] == CONST_VALUE) {
                if (checkLineX(pMinX.getyPoint(), pMaxX.getyPoint(), x)) {
                    // System.out.println("checkMoreLineY"+ " type: " + type);
                    return true;
                }
                x += type;
            }
        }
        return false;
    }

    /**
     * Check Pikachu matches.
     */
    public boolean algorithm(Pikachu one, Pikachu two) {
        if (matrix[one.getxPoint()][one.getyPoint()] == matrix[two.getxPoint()][two.getyPoint()]) {
            if (one.getxPoint() == two.getxPoint()) {
                if (this.checkLineX(one.getyPoint(), two.getyPoint(), one.getxPoint())) {
                    return true;
                }
            }
            if (one.getyPoint() == two.getyPoint()) {
                if (this.checkLineY(one.getxPoint(), two.getxPoint(), one.getyPoint())) {
                    return true;
                }
            }
            if (this.checkRectX(one, two)) {
                return true;
            }
            if (this.checkRectY(one, two)) {
                return true;
            }
            if (this.checkMoreLineX(one, two, 1)) {
                return true;
            }
            if (this.checkMoreLineX(one, two, -1)) {
                return true;
            }
            if (this.checkMoreLineY(one, two, 1)) {
                return true;
            }
            if (this.checkMoreLineY(one, two, -1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check can play.
     */

    public boolean canPlay() {
        for (int i = 1; i <= row - 2; i++) {
            for (int j = 1; j <= col - 2; j++) {
                if (matrix[i][j] != CONST_VALUE) {
                    for (int m = 1; m <= row - 2; m++) {
                        for (int n = 1; n <= col - 2; n++) {
                            if ((m != i || n != j) && matrix[m][n] != CONST_VALUE && matrix[m][n] == matrix[i][j]) {
                                if (algorithm(new Pikachu(i, j), new Pikachu(m, n))) {
                                    System.out.printf("i:" + i + " j:" + j + " ->" + "m:" + m + " n:" + n);
                                    System.out.println("---");
                                    System.out.println();
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Shuffle matrix when have no Pikachu couple valid
     */
    public void shuffleMatrix2() {
        List<int[]> nonZero = new ArrayList<>();
        List<int[]> ori = new ArrayList<>();
        for (int i = 1; i <= row - 2; i++) {
            for (int j = 1; j <= col - 2; j++) {
                if (matrix[i][j] != 0) {
                    nonZero.add(new int[]{i, j});
                    ori.add(new int[]{i, j});
                }
            }
        }

        Collections.shuffle(nonZero);

        for (int k = 0; k < nonZero.size(); k++) {
            int[] index = ori.get(k);
            int x = index[0];
            int y = index[1];
            int temp = this.matrix[x][y];

            int[] newIndex = nonZero.get(k);
            int newX = newIndex[0];
            int newY = newIndex[1];

            this.matrix[x][y] = this.matrix[newX][newY];
            // this.matrix[newX][newY] = temp;
        }
    }

    /**
     * Shuffle matrix when have no Pikachu couple valid
     */

    public void shuffleMatrix() {
        List<Integer> values = new ArrayList<>();

        for (int i = 1; i <= row - 2; i++) {
            for (int j = 1; j <= col - 2; j++) {
                if (matrix[i][j] != 0) {
                    values.add(matrix[i][j]);
                }
            }
        }

        Collections.shuffle(values);

        int index = 0;
        for (int i = 1; i <= row - 2; i++) {
            for (int j = 1; j <= col - 2; j++) {
                if (matrix[i][j] != 0) {
                    matrix[i][j] = values.get(index);
                    index++;
                }
            }
        }
    }

    public void printMatrix() {
        System.out.println("Ma tran hien tai");
        for (int i = 1; i <= row - 2; i++) {
            for (int j = 1; j <= col - 2; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void level2() {
        for (int i = MAP_COL; i >= 1; i--) {
            List<Integer> temp = new ArrayList<>();
            for (int j = MAP_ROW; j >= 1; j--) {
                if (matrix[j][i] != 0) {
                    temp.add(matrix[j][i]);
                }
            }
            int len = temp.size(), index = 0;
            for (int j = MAP_ROW; j >= 1; j--) {
                if (index >= len) {
                    this.matrix[j][i] = 0;
                } else {
                    this.matrix[j][i] = temp.get(index);
                    index++;
                }
            }
        }
    }
    public void level3 () {
        for (int i = MAP_ROW; i >= 1; i--) {
            List<Integer> temp = new ArrayList<>();
            for (int j = MAP_COL; j >= 1; j--) {
                if (matrix[i][j] != 0) {
                    temp.add(matrix[i][j]);
                }
            }
            int len = temp.size(), index = 0;
            for (int j = MAP_COL; j >= 1; j--) {
                if (index >= len) {
                    this.matrix[i][j] = 0;
                } else {
                    this.matrix[i][j] = temp.get(index);
                    index++;
                }
            }
        }
    }
    // End
}
