package com.fma.closingrepclient.model;


/**
 * Created by fma on 8/11/2017.
 */

public class ModelMaterial extends BaseModel {
    @TableField
    private String uid;

    @TableField
    private String nama;

    @TableField
    private String merk;

    @TableField
    private String jenis;

    @TableField
    private String satuan;

    @TableField
    private String template_qty;

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

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getTemplate_qty() {
        return template_qty;
    }

    public void setTemplate_qty(String template_qty) {
        this.template_qty = template_qty;
    }
}
