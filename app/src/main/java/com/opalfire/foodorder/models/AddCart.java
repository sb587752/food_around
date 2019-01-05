package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AddCart {
    @SerializedName("delivery_charges")
    @Expose
    private Integer deliveryCharges;
    @SerializedName("delivery_free_minimum")
    @Expose
    private Integer deliveryFreeMinimum;
    @SerializedName("carts")
    @Expose
    private List<Cart> products = new ArrayList();
    @SerializedName("tax_percentage")
    @Expose
    private Integer taxPercentage;

    public Integer getDeliveryCharges() {
        return this.deliveryCharges;
    }

    public void setDeliveryCharges(Integer num) {
        this.deliveryCharges = num;
    }

    public Integer getDeliveryFreeMinimum() {
        return this.deliveryFreeMinimum;
    }

    public void setDeliveryFreeMinimum(Integer num) {
        this.deliveryFreeMinimum = num;
    }

    public Integer getTaxPercentage() {
        return this.taxPercentage;
    }

    public void setTaxPercentage(Integer num) {
        this.taxPercentage = num;
    }

    public List<Cart> getProductList() {
        return this.products;
    }

    public void setProductList(List<Cart> list) {
        this.products = list;
    }
}
