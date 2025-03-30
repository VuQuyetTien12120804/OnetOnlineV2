package com.example.onetonline.business;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String email;
    private String userName;
    private String lastUpdate;
    private int level;

    public User(String id, String email, String userName, int level, String lastUpdate) {
        this.id = id;
        this.level = level;
        this.lastUpdate = lastUpdate;
        this.userName = userName;
        this.email = email;
    }

    public String userName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String id() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String email() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
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
