package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("transporter_id")
    @Expose
    private Integer transporterId;
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

    public Integer getTransporterId() {
        return this.transporterId;
    }

    public void setTransporterId(Integer num) {
        this.transporterId = num;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String str) {
        this.createdAt = str;
    }
}
