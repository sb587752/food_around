package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Addon_ {
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("id")
    @Expose
    private Integer id;
    private boolean isChecked = false;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public Addon_ withId(Integer num) {
        this.id = num;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Addon_ withName(String str) {
        this.name = str;
        return this;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer num) {
        this.shopId = num;
    }

    public Addon_ withShopId(Integer num) {
        this.shopId = num;
        return this;
    }

    public boolean getChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }

    public Object getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Object obj) {
        this.deletedAt = obj;
    }

    public Addon_ withDeletedAt(Object obj) {
        this.deletedAt = obj;
        return this;
    }
}
