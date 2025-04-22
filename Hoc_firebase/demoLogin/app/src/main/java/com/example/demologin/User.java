package com.example.demologin;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String password;
    private int level;
    private int isDeleted;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        level = 1;
        isDeleted = 0;
    }

    public User(String email, String password, int level, int isDeletedl) {
        this.email = email;
        this.isDeleted = isDeleted;
        this.level = level;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDeleted() {
        return isDeleted;
    }

    public void setDeleted(int deleted) {
        isDeleted = deleted;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
