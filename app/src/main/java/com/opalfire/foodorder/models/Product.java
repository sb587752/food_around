package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("addons")
    @Expose
    private List<Addon> addons = null;
    @SerializedName("avalability")
    @Expose
    private Integer avalability;
    @SerializedName("cart")
    @Expose
    private List<Cart> cart;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("featured_images")
    @Expose
    private List<FeaturedImage> featuredImages = null;
    @SerializedName("food_type")
    @Expose
    private String foodType;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("max_quantity")
    @Expose
    private Integer maxQuantity;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("prices")
    @Expose
    private Prices prices;
    @SerializedName("shop")
    @Expose
    private Shop shop;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("status")
    @Expose
    private String status;

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

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer num) {
        this.position = num;
    }

    public String getFoodType() {
        return this.foodType;
    }

    public void setFoodType(String str) {
        this.foodType = str;
    }

    public Integer getAvalability() {
        return this.avalability;
    }

    public void setAvalability(Integer num) {
        this.avalability = num;
    }

    public Integer getMaxQuantity() {
        return this.maxQuantity;
    }

    public void setMaxQuantity(Integer num) {
        this.maxQuantity = num;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public Prices getPrices() {
        return this.prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public List<FeaturedImage> getFeaturedImages() {
        return this.featuredImages;
    }

    public void setFeaturedImages(List<FeaturedImage> list) {
        this.featuredImages = list;
    }

    public List<Cart> getCart() {
        return this.cart;
    }

    public void setCart(List<Cart> list) {
        this.cart = list;
    }

    public List<Addon> getAddons() {
        return this.addons;
    }

    public void setAddons(List<Addon> list) {
        this.addons = list;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public void setImages(List<Image> list) {
        this.images = list;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
