package com.opalfire.foodorder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart {
    @SerializedName("cart_addons")
    @Expose
    private List<CartAddon> cartAddons = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("note")
    @Expose
    private Object note;
    @SerializedName("order_id")
    @Expose
    private Object orderId;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("promocode_id")
    @Expose
    private Object promocodeId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("savedforlater")
    @Expose
    private Integer savedforlater;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer num) {
        this.productId = num;
    }

    public Object getPromocodeId() {
        return this.promocodeId;
    }

    public void setPromocodeId(Object obj) {
        this.promocodeId = obj;
    }

    public Object getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Object obj) {
        this.orderId = obj;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer num) {
        this.quantity = num;
    }

    public Object getNote() {
        return this.note;
    }

    public void setNote(Object obj) {
        this.note = obj;
    }

    public Integer getSavedforlater() {
        return this.savedforlater;
    }

    public void setSavedforlater(Integer num) {
        this.savedforlater = num;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<CartAddon> getCartAddons() {
        return this.cartAddons;
    }

    public void setCartAddons(List<CartAddon> list) {
        this.cartAddons = list;
    }
}
