package com.opalfire.orderaround.models;

import java.util.ArrayList;
import java.util.List;

public class FavListModel {
    List<Available> availables = new ArrayList();
    String header;

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String str) {
        this.header = str;
    }

    public List<Available> getFav() {
        return this.availables;
    }

    public void setFav(List<Available> list) {
        this.availables = list;
    }
}
