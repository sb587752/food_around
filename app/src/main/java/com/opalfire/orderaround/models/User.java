package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("avatar")
    @Expose
    private Object avatar;
    @SerializedName("cart")
    @Expose
    private List<Cart> cart = null;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("login_by")
    @Expose
    private String loginBy;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("social_unique_id")
    @Expose
    private Object socialUniqueId;
    @SerializedName("stripe_cust_id")
    @Expose
    private Object stripeCustId;
    @SerializedName("wallet_balance")
    @Expose
    private Integer walletBalance;

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

    public Object getAvatar() {
        return this.avatar;
    }

    public void setAvatar(Object obj) {
        this.avatar = obj;
    }

    public String getDeviceToken() {
        return this.deviceToken;
    }

    public void setDeviceToken(String str) {
        this.deviceToken = str;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String str) {
        this.deviceType = str;
    }

    public String getLoginBy() {
        return this.loginBy;
    }

    public void setLoginBy(String str) {
        this.loginBy = str;
    }

    public Object getSocialUniqueId() {
        return this.socialUniqueId;
    }

    public void setSocialUniqueId(Object obj) {
        this.socialUniqueId = obj;
    }

    public Object getStripeCustId() {
        return this.stripeCustId;
    }

    public void setStripeCustId(Object obj) {
        this.stripeCustId = obj;
    }

    public Integer getWalletBalance() {
        return this.walletBalance;
    }

    public void setWalletBalance(Integer num) {
        this.walletBalance = num;
    }

    public String getOtp() {
        return this.otp;
    }

    public void setOtp(String str) {
        this.otp = str;
    }

    public List<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(List<Address> list) {
        this.addresses = list;
    }

    public List<Cart> getCart() {
        return this.cart;
    }

    public void setCart(List<Cart> list) {
        this.cart = list;
    }
}
