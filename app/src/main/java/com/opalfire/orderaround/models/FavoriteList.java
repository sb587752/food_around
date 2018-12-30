package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteList {
    @SerializedName("available")
    @Expose
    private List<Available> available = null;
    @SerializedName("un_available")
    @Expose
    private List<UnAvailable> unAvailable = null;

    public List<Available> getAvailable() {
        return this.available;
    }

    public void setAvailable(List<Available> list) {
        this.available = list;
    }

    public FavoriteList withAvailable(List<Available> list) {
        this.available = list;
        return this;
    }

    public List<UnAvailable> getUnAvailable() {
        return this.unAvailable;
    }

    public void setUnAvailable(List<UnAvailable> list) {
        this.unAvailable = list;
    }

    public FavoriteList withUnAvailable(List<UnAvailable> list) {
        this.unAvailable = list;
        return this;
    }
}
