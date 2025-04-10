package com.example.onetonline.data;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String userName;
    private String lastUpdate;
    private int level;
    private int score;
    private int exp;
    private Boolean isDeleted;

    public User(String id, String userName, int level, int score, int exp, String lastUpdate, Boolean isDeleted) {
        this.id = id;
        this.userName = userName;
        this.level = level;
        this.score = score;
        this.exp = exp;
        this.lastUpdate = lastUpdate;
        this.isDeleted = isDeleted;
    }

    public User(){
        this.id = "I'm Batman";
        this.userName = "Guest";
        this.level = 0;
        this.score = 0;
        this.exp = 0;
        this.lastUpdate = "";
        this.isDeleted = false;
    }

    public String userName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public int score() {
        return score;
    }

    public User setScore(int score) {
        this.score = score;
        return this;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public User setDeleted(Boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    public int exp() {
        return exp;
    }

    public User setExp(int exp) {
        this.exp = exp;
        return this;
    }

    public String id() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String lastUpdate() {
        return lastUpdate;
    }

    public User setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public int level() {
        return level;
    }

    public User setLevel(int level) {
        this.level = level;
        return this;
    }
}
