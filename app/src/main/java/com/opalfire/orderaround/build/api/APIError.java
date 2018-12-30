package com.opalfire.orderaround.build.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIError {
    @SerializedName("type")
    @Expose
    private List<String> type = null;

    public List<String> getType() {
        return this.type;
    }

    public void setType(List<String> list) {
        this.type = list;
    }

    public APIError withType(List<String> list) {
        this.type = list;
        return this;
    }
}
