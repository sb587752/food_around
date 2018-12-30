package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("building")
    @Expose
    private String building;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("map_address")
    @Expose
    private String mapAddress;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer num) {
        this.userId = num;
    }

    public String getBuilding() {
        return this.building;
    }

    public void setBuilding(String str) {
        this.building = str;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String str) {
        this.street = str;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String str) {
        this.state = str;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public String getPincode() {
        return this.pincode;
    }

    public void setPincode(String str) {
        this.pincode = str;
    }

    public String getLandmark() {
        return this.landmark;
    }

    public void setLandmark(String str) {
        this.landmark = str;
    }

    public String getMapAddress() {
        return this.mapAddress;
    }

    public void setMapAddress(String str) {
        this.mapAddress = str;
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

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }
}
