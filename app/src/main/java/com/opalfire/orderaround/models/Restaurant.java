package com.opalfire.orderaround.models;

public class Restaurant {
    public String category;
    public String distance;
    public String name;
    public String offer;
    public String price;
    public String rating;
    public String restaurantInfo;

    public Restaurant(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.name = str;
        this.category = str2;
        this.offer = str3;
        this.rating = str4;
        this.distance = str5;
        this.restaurantInfo = str7;
        this.price = str6;
    }
}
