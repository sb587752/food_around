package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("shops")
    @Expose
    private List<Shop> shops = null;

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> list) {
        this.products = list;
    }

    public List<Shop> getShops() {
        return this.shops;
    }

    public void setShops(List<Shop> list) {
        this.shops = list;
    }
}
