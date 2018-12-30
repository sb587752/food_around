package com.opalfire.orderaround.models;

public class RecommendedDish {
    String avaialable;
    String category;
    String description;
    String imgUrl;
    Boolean isVeg;
    String name;
    String price;

    public RecommendedDish(String str, String str2, String str3, Boolean bool, String str4, String str5, String str6) {
        this.name = str;
        this.category = str2;
        this.price = str3;
        this.isVeg = bool;
        this.imgUrl = str4;
        this.description = str5;
        this.avaialable = str6;
    }

    public String getAvaialable() {
        return this.avaialable;
    }

    public void setAvaialable(String str) {
        this.avaialable = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public Boolean getIsVeg() {
        return this.isVeg;
    }

    public void setIsVeg(Boolean bool) {
        this.isVeg = bool;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String str) {
        this.imgUrl = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }
}
