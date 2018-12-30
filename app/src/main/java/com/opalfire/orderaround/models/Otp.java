package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Otp {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("otp")
    @Expose
    private Integer otp;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public Integer getOtp() {
        return this.otp;
    }

    public void setOtp(Integer num) {
        this.otp = num;
    }
}
