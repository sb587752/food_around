package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parent_id")
    @Expose
    private Object parentId;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public Object getParentId() {
        return this.parentId;
    }

    public void setParentId(Object obj) {
        this.parentId = obj;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer num) {
        this.shopId = num;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer num) {
        this.position = num;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> list) {
        this.products = list;
    }
}
