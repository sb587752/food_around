package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Addon {
    @SerializedName("addon")
    @Expose
    private Addon_ addon;
    @SerializedName("addon_id")
    @Expose
    private Integer addonId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    private Integer quantity;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public Integer getAddonId() {
        return this.addonId;
    }

    public void setAddonId(Integer num) {
        this.addonId = num;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer num) {
        this.productId = num;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer num) {
        this.quantity = num;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer num) {
        this.price = num;
    }

    public Addon_ getAddon() {
        return this.addon;
    }

    public void setAddon(Addon_ addon_) {
        this.addon = addon_;
    }
}
