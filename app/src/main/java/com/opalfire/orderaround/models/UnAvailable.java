package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnAvailable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shop")
    @Expose
    private Shop shop;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer num) {
        this.shopId = num;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer num) {
        this.userId = num;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
