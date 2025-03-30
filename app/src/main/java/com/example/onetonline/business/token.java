package com.example.onetonline.business;

public class token {
    private String access_token;

    public String access_token() {
        return access_token;
    }

    public token(String access_token) {
        this.access_token = access_token;
    }

    public token setAccess_token(String access_token) {
        this.access_token = access_token;
        return this;
    }
}
