package com.example.nirma_canteen_management;

import java.util.ArrayList;

public class PlacedOrder {
    private String username;
    private  String time;
    private  ArrayList<OrderItem> order = new ArrayList<>();

    public PlacedOrder() {
    }

    public PlacedOrder(String username, String time, ArrayList<OrderItem> order) {
        this.username = username;
        this.time = time;
        this.order = order;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<OrderItem> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<OrderItem> order) {
        this.order = order;
    }
}
