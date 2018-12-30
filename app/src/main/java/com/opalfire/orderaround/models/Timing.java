package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timing {
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("start_time")
    @Expose
    private String startTime;

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

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String str) {
        this.day = str;
    }
}
