package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("id")
    @Expose
    private Integer id;
    private boolean isChecked = false;
    @SerializedName("is_default")
    @Expose
    private Integer isDefault;
    @SerializedName("last_four")
    @Expose
    private String lastFour;
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

    public String getLastFour() {
        return this.lastFour;
    }

    public void setLastFour(String str) {
        this.lastFour = str;
    }

    public String getCardId() {
        return this.cardId;
    }

    public void setCardId(String str) {
        this.cardId = str;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String str) {
        this.brand = str;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }

    public Integer getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Integer num) {
        this.isDefault = num;
    }
}
