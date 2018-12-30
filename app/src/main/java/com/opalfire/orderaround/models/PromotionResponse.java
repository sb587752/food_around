package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromotionResponse {
    @SerializedName("wallet_balance")
    @Expose
    private Integer walletMoney;

    public Integer getWalletMoney() {
        return this.walletMoney;
    }

    public void setWalletMoney(Integer num) {
        this.walletMoney = num;
    }
}
