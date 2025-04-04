package com.example.onetonline.presentation.model;
import android.icu.text.SymbolTable;

import com.example.onetonline.business.UserData;
import com.example.onetonline.data.myDBHelper;

import java.io.Serializable;

public class UserInf implements Serializable {
    private String userName;
    private int level;
    private int score;
    private int exp;
    private String last_update;

    public UserInf(String userName, int level, int score, int exp, String last_update) {
        this.userName = userName;
        this.level = level;
        this.score = score;
        this.exp = exp;
        this.last_update = last_update;
    }

    public String userName() {
        return userName;
    }

    public UserInf setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String last_update() {
        return last_update;
    }

    public UserInf setLast_update(String last_update) {
        this.last_update = last_update;
        return this;
    }

    public int exp() {
        return exp;
    }

    public UserInf setExp(int exp) {
        this.exp = exp;
        return this;
    }

    public int score() {
        return score;
    }

    public UserInf setScore(int score) {
        this.score = score;
        return this;
    }

    public int level() {
        return level;
    }

    public UserInf setLevel(int level) {
        this.level = level;
        return this;
    }

}
