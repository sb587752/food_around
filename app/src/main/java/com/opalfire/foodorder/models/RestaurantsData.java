package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantsData {
    @SerializedName("banners")
    @Expose
    private List<Banner> banners = null;
    @SerializedName("shops")
    @Expose
    private List<Shop> shops = null;

    public List<Shop> getShops() {
        return this.shops;
    }

    public void setShops(List<Shop> list) {
        this.shops = list;
    }

    public List<Banner> getBanners() {
        return this.banners;
    }

    public void setBanners(List<Banner> list) {
        this.banners = list;
    }
}
