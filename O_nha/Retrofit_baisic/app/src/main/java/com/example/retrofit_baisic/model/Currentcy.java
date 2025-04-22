package com.example.retrofit_baisic.model;

public class Currentcy {
    private String login;
    private int id;
    private String node_id;
    private String followers_url;

    public Currentcy(String followers_url, int id, String login, String node_id) {
        this.followers_url = followers_url;
        this.id = id;
        this.login = login;
        this.node_id = node_id;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }
}
