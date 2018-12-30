package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot {
    @SerializedName("cuisine_id")
    @Expose
    private Integer cuisineId;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer num) {
        this.shopId = num;
    }

    public Integer getCuisineId() {
        return this.cuisineId;
    }

    public void setCuisineId(Integer num) {
        this.cuisineId = num;
    }
}
