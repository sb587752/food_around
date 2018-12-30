package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ratings {
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;

    public String getRating() {
        return this.rating;
    }

    public void setRating(String str) {
        this.rating = str;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer num) {
        this.shopId = num;
    }
}
