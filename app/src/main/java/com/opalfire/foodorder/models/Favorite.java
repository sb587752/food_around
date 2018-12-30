package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorite {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public String getShopId() {
        return this.shopId;
    }

    public void setShopId(String str) {
        this.shopId = str;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer num) {
        this.userId = num;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }
}
