package com.opalfire.orderaround.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("dispute")
    @Expose
    private String dispute;
    @SerializedName("disputes")
    @Expose
    private List<Object> disputes = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("invoice")
    @Expose
    private Invoice invoice;
    @SerializedName("invoice_id")
    @Expose
    private String invoiceId;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("note")
    @Expose
    private Object note;
    @SerializedName("order_otp")
    @Expose
    private Integer orderOtp;
    @SerializedName("order_ready_status")
    @Expose
    private Integer orderReadyStatus;
    @SerializedName("order_ready_time")
    @Expose
    private Integer orderReadyTime;
    @SerializedName("ordertiming")
    @Expose
    private List<Ordertiming> ordertiming;
    @SerializedName("reason")
    @Expose
    private Object reason;
    @SerializedName("reviewrating")
    @Expose
    private Object reviewrating;
    @SerializedName("route_key")
    @Expose
    private String routeKey;
    @SerializedName("schedule_status")
    @Expose
    private Integer scheduleStatus;
    @SerializedName("shift_id")
    @Expose
    private Integer shiftId;
    @SerializedName("shop")
    @Expose
    private Shop shop;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("transporter")
    @Expose
    private Transporter transporter;
    @SerializedName("transporter_id")
    @Expose
    private Integer transporterId;
    @SerializedName("transporter_vehicle_id")
    @Expose
    private Integer transporterVehicleId;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("user_address_id")
    @Expose
    private Integer userAddressId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("vehicles")
    @Expose
    private Vehicles vehicles;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public String getInvoiceId() {
        return this.invoiceId;
    }

    public void setInvoiceId(String str) {
        this.invoiceId = str;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer num) {
        this.userId = num;
    }

    public Integer getShiftId() {
        return this.shiftId;
    }

    public void setShiftId(Integer num) {
        this.shiftId = num;
    }

    public Integer getUserAddressId() {
        return this.userAddressId;
    }

    public void setUserAddressId(Integer num) {
        this.userAddressId = num;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer num) {
        this.shopId = num;
    }

    public Integer getTransporterId() {
        return this.transporterId;
    }

    public void setTransporterId(Integer num) {
        this.transporterId = num;
    }

    public Integer getTransporterVehicleId() {
        return this.transporterVehicleId;
    }

    public void setTransporterVehicleId(Integer num) {
        this.transporterVehicleId = num;
    }

    public Object getReason() {
        return this.reason;
    }

    public void setReason(Object obj) {
        this.reason = obj;
    }

    public Object getNote() {
        return this.note;
    }

    public void setNote(Object obj) {
        this.note = obj;
    }

    public String getRouteKey() {
        return this.routeKey;
    }

    public void setRouteKey(String str) {
        this.routeKey = str;
    }

    public String getDispute() {
        return this.dispute;
    }

    public void setDispute(String str) {
        this.dispute = str;
    }

    public String getDeliveryDate() {
        return this.deliveryDate;
    }

    public void setDeliveryDate(String str) {
        this.deliveryDate = str;
    }

    public Integer getOrderOtp() {
        return this.orderOtp;
    }

    public void setOrderOtp(Integer num) {
        this.orderOtp = num;
    }

    public Integer getOrderReadyTime() {
        return this.orderReadyTime;
    }

    public void setOrderReadyTime(Integer num) {
        this.orderReadyTime = num;
    }

    public Integer getOrderReadyStatus() {
        return this.orderReadyStatus;
    }

    public void setOrderReadyStatus(Integer num) {
        this.orderReadyStatus = num;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public Integer getScheduleStatus() {
        return this.scheduleStatus;
    }

    public void setScheduleStatus(Integer num) {
        this.scheduleStatus = num;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String str) {
        this.createdAt = str;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Transporter getTransporter() {
        return this.transporter;
    }

    public void setTransporter(Transporter transporter) {
        this.transporter = transporter;
    }

    public Vehicles getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(Vehicles vehicles) {
        this.vehicles = vehicles;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> list) {
        this.items = list;
    }

    public List<Ordertiming> getOrdertiming() {
        return this.ordertiming;
    }

    public void setOrdertiming(List<Ordertiming> list) {
        this.ordertiming = list;
    }

    public List<Object> getDisputes() {
        return this.disputes;
    }

    public void setDisputes(List<Object> list) {
        this.disputes = list;
    }

    public Object getReviewrating() {
        return this.reviewrating;
    }

    public void setReviewrating(Object obj) {
        this.reviewrating = obj;
    }
}
