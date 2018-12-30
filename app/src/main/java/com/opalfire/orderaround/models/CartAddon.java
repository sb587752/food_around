package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartAddon {
    @SerializedName("addon_product")
    @Expose
    private Addon addonProduct;
    @SerializedName("addon_product_id")
    @Expose
    private Integer addonProductId;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("user_cart_id")
    @Expose
    private Integer userCartId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public Integer getUserCartId() {
        return this.userCartId;
    }

    public void setUserCartId(Integer num) {
        this.userCartId = num;
    }

    public Integer getAddonProductId() {
        return this.addonProductId;
    }

    public void setAddonProductId(Integer num) {
        this.addonProductId = num;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer num) {
        this.quantity = num;
    }

    public Object getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Object obj) {
        this.deletedAt = obj;
    }

    public Addon getAddonProduct() {
        return this.addonProduct;
    }

    public void setAddonProduct(Addon addon) {
        this.addonProduct = addon;
    }
}
