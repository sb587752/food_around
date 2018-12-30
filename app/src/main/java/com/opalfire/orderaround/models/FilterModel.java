package com.opalfire.orderaround.models;

import java.util.ArrayList;
import java.util.List;

public class FilterModel {
    List<Cuisine> filters = new ArrayList();
    String header;

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String str) {
        this.header = str;
    }

    public List<Cuisine> getCuisines() {
        return this.filters;
    }

    public void setCuisines(List<Cuisine> list) {
        this.filters = list;
    }
}
