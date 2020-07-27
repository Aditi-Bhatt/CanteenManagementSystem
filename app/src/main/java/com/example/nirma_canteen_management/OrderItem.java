package com.example.nirma_canteen_management;

public class OrderItem {
    private String name;
    private  String imageurl;
    private int price;
    private int quantity;
    private String OrderId;

    public OrderItem() {
    }

    public OrderItem(String name, String imageurl, int price, int quantity) {
        this.name = name;
        this.imageurl = imageurl;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
