package com.fma.closingrepclient.model;


import java.util.Date;

/**
 * Created by fma on 8/11/2017.
 */

public class ModelOrder extends BaseModel {
    @Override
    public String getTableName() {
        return "orders";
    }

    @TableField
    private String uid;

    @TableField
    private String orderno;

    @TableField
    private Date tanggal;

    @TableField
    private int customer_id;

    @TableField
    private String phone;

    @TableField
    private String inetnumber;

    @TableField
    private int area_id;

    @TableField
    private int product_id;

    @TableField
    private String jenis_order;

    @TableField
    private int user_id;

    @TableField
    private String status;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInetnumber() {
        return inetnumber;
    }

    public void setInetnumber(String inetnumber) {
        this.inetnumber = inetnumber;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getJenis_order() {
        return jenis_order;
    }

    public void setJenis_order(String jenis_order) {
        this.jenis_order = jenis_order;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
