package com.opalfire.orderaround.models;

public class Location {
    public String address;
    public Integer icon_id;
    public String name;

    public Location(String str, String str2, Integer num) {
        this.name = str;
        this.address = str2;
        this.icon_id = num;
    }
}
