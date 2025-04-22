package com.example.on_tap;

public class Singer {
    private String song;
    private String name;
    private float time;

    public Singer(String name, String song, float time) {
        this.name = name;
        this.song = song;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
