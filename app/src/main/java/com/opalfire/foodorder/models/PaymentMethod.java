package com.opalfire.foodorder.models;

public class PaymentMethod {
    public Integer icon_id;
    public String name;

    public PaymentMethod(String str, Integer num) {
        this.name = str;
        this.icon_id = num;
    }
}
