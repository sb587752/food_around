package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Promotions {
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("expiration")
    @Expose
    private String expiration;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("promocode_type")
    @Expose
    private String promocodeType;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public String getPromoCode() {
        return this.promoCode;
    }

    public void setPromoCode(String str) {
        this.promoCode = str;
    }

    public String getPromocodeType() {
        return this.promocodeType;
    }

    public void setPromocodeType(String str) {
        this.promocodeType = str;
    }

    public Integer getDiscount() {
        return this.discount;
    }

    public void setDiscount(Integer num) {
        this.discount = num;
    }

    public String getExpiration() {
        return this.expiration;
    }

    public void setExpiration(String str) {
        this.expiration = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
