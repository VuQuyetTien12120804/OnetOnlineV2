package com.example.onetonline.business;

public class PostResponse {
    private String message;
    private String user_id;

    public PostResponse(String user_id, String message) {
        this.user_id = user_id;
        this.message = message;
    }

    public String message() {
        return message;
    }

    public PostResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String user_id() {
        return user_id;
    }

    public PostResponse setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }
}