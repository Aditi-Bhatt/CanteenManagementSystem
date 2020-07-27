package com.example.nirma_canteen_management;

public class FoodItem {
    private String name;
    private  String imageurl;
    private int price;

    public FoodItem() {
    }

    public FoodItem(String name, int price, String imageurl) {
        this.name = name;
        this.imageurl = imageurl;
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "name='" + name + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", price=" + price +
                '}';
    }
}
