package com.example.stupp.models;

import java.util.List;

public class User {

    public User(String username, String user_id, List<String> order_id) {
        this.username = username;
        this.user_id = user_id;
        this.order_id = order_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<String> getOrder_id() {
        return order_id;
    }

    public void setOrder_id(List<String> order_id) {
        this.order_id = order_id;
    }

    private String username;
    private String user_id;
    private List<String> order_id;

    public User(){}


}
