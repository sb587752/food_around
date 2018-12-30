package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ShopDetail {
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("featured_products")
    @Expose
    private List<Product> featuredProducts = new ArrayList();

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> list) {
        this.categories = list;
    }

    public List<Product> getFeaturedProducts() {
        return this.featuredProducts;
    }

    public void setFeaturedProducts(List<Product> list) {
        this.featuredProducts = list;
    }
}
