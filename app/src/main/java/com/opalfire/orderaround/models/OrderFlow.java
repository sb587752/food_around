package com.opalfire.orderaround.models;

public class OrderFlow {
    public String status;
    public String statusDescription;
    public int statusImage;
    public String statusTitle;

    public OrderFlow(String str, String str2, int i, String str3) {
        this.statusImage = i;
        this.statusTitle = str;
        this.statusDescription = str2;
        this.status = str3;
    }
}
