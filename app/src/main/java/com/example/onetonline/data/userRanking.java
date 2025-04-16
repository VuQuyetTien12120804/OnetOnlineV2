package com.example.onetonline.data;

public class userRanking {
    private String userName;
    private int score;
    private int level;

    public userRanking(String userName, int score, int level) {
        this.userName = userName;
        this.score = score;
        this.level = level;
    }

    public String userName() {
        return userName;
    }

    public userRanking setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public int score() {
        return score;
    }

    public userRanking setScore(int score) {
        this.score = score;
        return this;
    }

    public int level() {
        return level;
    }

    public userRanking setLevel(int level) {
        this.level = level;
        return this;
    }
}
