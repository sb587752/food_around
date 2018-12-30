package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMoney {
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("braintree_id")
    @Expose
    private Object braintreeId;
    @SerializedName("currency")
    @Expose
    private String currency;
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
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("social_unique_id")
    @Expose
    private String socialUniqueId;
    @SerializedName("stripe_cust_id")
    @Expose
    private String stripeCustId;
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

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
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

    public String getSocialUniqueId() {
        return this.socialUniqueId;
    }

    public void setSocialUniqueId(String str) {
        this.socialUniqueId = str;
    }

    public String getStripeCustId() {
        return this.stripeCustId;
    }

    public void setStripeCustId(String str) {
        this.stripeCustId = str;
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

    public Object getBraintreeId() {
        return this.braintreeId;
    }

    public void setBraintreeId(Object obj) {
        this.braintreeId = obj;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String str) {
        this.currency = str;
    }

    public String getPaymentMode() {
        return this.paymentMode;
    }

    public void setPaymentMode(String str) {
        this.paymentMode = str;
    }
}
