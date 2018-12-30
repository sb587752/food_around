package com.opalfire.foodorder.models;

public class ImpressiveDish {
    String imgUrl;
    String name;

    public ImpressiveDish(String str, String str2) {
        this.name = str;
        this.imgUrl = str2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String str) {
        this.imgUrl = str;
    }
}
