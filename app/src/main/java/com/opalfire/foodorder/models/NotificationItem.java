package com.opalfire.foodorder.models;

public class NotificationItem {
    public String offerCode;
    public String offerDescription;
    public String validity;

    public NotificationItem(String str, String str2, String str3) {
        this.offerDescription = str;
        this.offerCode = str2;
        this.validity = str3;
    }
}
