package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Invoice {
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("delivery_charge")
    @Expose
    private Integer deliveryCharge;
    @SerializedName("discount")
    @Expose
    private Double discount;
    @SerializedName("gross")
    @Expose
    private Double gross;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("net")
    @Expose
    private Double net;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("paid")
    @Expose
    private Integer paid;
    @SerializedName("payable")
    @Expose
    private Double payable;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tax")
    @Expose
    private Double tax;
    @SerializedName("tender_pay")
    @Expose
    private Object tenderPay;
    @SerializedName("total_pay")
    @Expose
    private Object totalPay;
    @SerializedName("wallet_amount")
    @Expose
    private Double walletAmount;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer num) {
        this.orderId = num;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer num) {
        this.quantity = num;
    }

    public Integer getPaid() {
        return this.paid;
    }

    public void setPaid(Integer num) {
        this.paid = num;
    }

    public Double getGross() {
        return this.gross;
    }

    public void setGross(Double d) {
        this.gross = d;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double d) {
        this.discount = d;
    }

    public Integer getDeliveryCharge() {
        return this.deliveryCharge;
    }

    public void setDeliveryCharge(Integer num) {
        this.deliveryCharge = num;
    }

    public Double getTax() {
        return this.tax;
    }

    public void setTax(Double d) {
        this.tax = d;
    }

    public Double getNet() {
        return this.net;
    }

    public void setNet(Double d) {
        this.net = d;
    }

    public Object getTotalPay() {
        return this.totalPay;
    }

    public void setTotalPay(Object obj) {
        this.totalPay = obj;
    }

    public Double getWalletAmount() {
        return this.walletAmount;
    }

    public void setWalletAmount(Double d) {
        this.walletAmount = d;
    }

    public Double getPayable() {
        return this.payable;
    }

    public void setPayable(Double d) {
        this.payable = d;
    }

    public Object getTenderPay() {
        return this.tenderPay;
    }

    public void setTenderPay(Object obj) {
        this.tenderPay = obj;
    }

    public String getPaymentMode() {
        return this.paymentMode;
    }

    public void setPaymentMode(String str) {
        this.paymentMode = str;
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
}
