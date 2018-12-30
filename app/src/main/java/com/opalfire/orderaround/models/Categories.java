package com.opalfire.orderaround.models;

import java.util.ArrayList;
import java.util.List;

public class Categories {
    String header;
    List<Product> productList = new ArrayList();

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String str) {
        this.header = str;
    }

    public List<Product> getProductList() {
        return this.productList;
    }

    public void setOrders(List<Product> list) {
        this.productList = list;
    }
}
