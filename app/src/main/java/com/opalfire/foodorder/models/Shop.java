package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Shop {
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("cuisines")
    @Expose
    private List<Cuisine> cuisines = null;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("estimated_delivery_time")
    @Expose
    private Integer estimatedDeliveryTime;
    @SerializedName("favorite")
    @Expose
    private Favorite favorite;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("maps_address")
    @Expose
    private String mapsAddress;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("offer_min_amount")
    @Expose
    private Double offerMinAmount;
    @SerializedName("offer_percent")
    @Expose
    private Integer offerPercent;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("pure_veg")
    @Expose
    private Integer pureVeg;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("ratings")
    @Expose
    private Ratings ratings;
    @SerializedName("shopstatus")
    @Expose
    private String shopstatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("timings")
    @Expose
    private List<Timing> timings = null;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public Double getOfferMinAmount() {
        return this.offerMinAmount;
    }

    public void setOfferMinAmount(Double d) {
        this.offerMinAmount = d;
    }

    public Integer getOfferPercent() {
        return this.offerPercent;
    }

    public void setOfferPercent(Integer num) {
        this.offerPercent = num;
    }

    public Integer getEstimatedDeliveryTime() {
        return this.estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(Integer num) {
        this.estimatedDeliveryTime = num;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public String getMapsAddress() {
        return this.mapsAddress;
    }

    public void setMapsAddress(String str) {
        this.mapsAddress = str;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double d) {
        this.latitude = d;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double d) {
        this.longitude = d;
    }

    public Integer getPureVeg() {
        return this.pureVeg;
    }

    public void setPureVeg(Integer num) {
        this.pureVeg = num;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String str) {
        this.createdAt = str;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String str) {
        this.updatedAt = str;
    }

    public Object getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Object obj) {
        this.deletedAt = obj;
    }

    public Double getDistance() {
        return this.distance;
    }

    public void setDistance(Double d) {
        this.distance = d;
    }

    public List<Cuisine> getCuisines() {
        return this.cuisines;
    }

    public void setCuisines(List<Cuisine> list) {
        this.cuisines = list;
    }

    public List<Timing> getTimings() {
        return this.timings;
    }

    public void setTimings(List<Timing> list) {
        this.timings = list;
    }

    public Ratings getRatings() {
        return this.ratings;
    }

    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> list) {
        this.categories = list;
    }

    public Favorite getFavorite() {
        return this.favorite;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double d) {
        this.rating = d;
    }

    public String getShopstatus() {
        return this.shopstatus;
    }

    public void setShopstatus(String str) {
        this.shopstatus = str;
    }
}
