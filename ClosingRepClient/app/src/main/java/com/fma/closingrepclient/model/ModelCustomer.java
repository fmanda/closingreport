package com.fma.closingrepclient.model;


/**
 * Created by fma on 8/11/2017.
 */

public class ModelCustomer extends BaseModel {
    @TableField
    private String uid;

    @TableField
    private String nama;

    @TableField
    private String alamat;

    @TableField
    private String ktpno;

    @TableField
    private String custphone;

    @TableField
    private int area_id;

    @TableField
    private Double lng;

    @TableField
    private Double lat;

    @TableField
    private String phone;

    @TableField
    private String inetnumber;

    private String area_uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKtpno() {
        return ktpno;
    }

    public void setKtpno(String ktpno) {
        this.ktpno = ktpno;
    }

    public String getCustphone() {
        return custphone;
    }

    public void setCustphone(String custphone) {
        this.custphone = custphone;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
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

    public String getArea_uid() {
        return area_uid;
    }

    public void setArea_uid(String area_uid) {
        this.area_uid = area_uid;
    }
}
