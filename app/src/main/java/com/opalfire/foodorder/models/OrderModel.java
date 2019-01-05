package com.opalfire.foodorder.models;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    String header;
    List<Order> orders = new ArrayList();

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String str) {
        this.header = str;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> list) {
        this.orders = list;
    }
}
