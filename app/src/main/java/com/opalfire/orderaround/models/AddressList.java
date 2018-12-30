package com.opalfire.orderaround.models;

import java.util.ArrayList;
import java.util.List;

public class AddressList {
    List<Address> addresses = new ArrayList();
    String header;

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String str) {
        this.header = str;
    }

    public List<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(List<Address> list) {
        this.addresses = list;
    }
}
