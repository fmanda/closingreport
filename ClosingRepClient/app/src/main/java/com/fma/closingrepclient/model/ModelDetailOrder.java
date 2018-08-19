package com.fma.closingrepclient.model;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fma.closingrepclient.helper.RandomString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by fma on 8/11/2017.
 */

public class ModelDetailOrder extends BaseModel {
    @Override
    public String getTableName() {
        return "detailorder";
    }

    @TableField
    private String uid;

    @TableField
    private Date tanggal = new Date();

    @TableField
    private String nobukti;

    @TableField
    private Integer order_id;
    private String order_uid;

    @TableField
    private Integer user_id;
//    private Integer user_uid;

    @TableField
    private String odp;

    @TableField
    private String qrcode;

    @TableField
    private String status;

    @TableField
    private String keterangan;

    @TableField
    private Integer uploaded;

    public ModelOrder order;

    public List<ModelDetailOrderMaterial> items = new ArrayList<ModelDetailOrderMaterial>();

    public void loadOrder(SQLiteDatabase db) {
        order = new ModelOrder();
        order.loadFromDB(db, this.order_id);
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getNobukti() {
        return nobukti;
    }

    public void setNobukti(String nobukti) {
        this.nobukti = nobukti;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_uid() {
        return order_uid;
    }

    public void setOrder_uid(String order_uid) {
        this.order_uid = order_uid;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getOdp() {
        return odp;
    }

    public void setOdp(String odp) {
        this.odp = odp;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void reLoadAll(SQLiteDatabase db){
        this.loadOrder(db);
        this.items.clear();
        String sql = "select * from detailordermaterial where detailorder_id = " + String.valueOf(this.getId());

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            ModelDetailOrderMaterial material = new ModelDetailOrderMaterial();
            material.loadFromCursor(cursor);
            material.loadMaterial(db);
            this.items.add(material);
        }
    }

    public void prepareUpload(SQLiteDatabase db){
        if (order_id > 0) {
            loadOrder(db);
            this.order_uid = order.getUid();
        }

        this.reLoadAll(db);
        for (ModelDetailOrderMaterial material : items) {
            material.prepareUpload(db);
        }

    }


    public void saveToDBAll(SQLiteDatabase db) {
        Log.d("DB","Order.saveToDBAll");
        db.beginTransaction();
        try {
            super.saveToDB(db ,true);
            ModelDetailOrderMaterial tmp = new ModelDetailOrderMaterial();
            db.execSQL(tmp.generateSQLDelete("where detailorder_id = " + String.valueOf(getId())));

            for (ModelDetailOrderMaterial material : items) {
                material.setId(0); //force insert
                material.setDetailorder_id(this.getId());
                material.saveToDB(db);
            }

            db.execSQL("update orders set status = '" + this.getStatus() + "' where id = " + String.valueOf(this.getOrder_id()));
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public ModelDetailOrder() {
        this.generateNoBukti();
    }

    public void generateNoBukti(){
        RandomString gen = new RandomString(12, ThreadLocalRandom.current());
        this.nobukti = gen.nextString();
    }

    public Integer getUploaded() {
        return uploaded;
    }

    public void setUploaded(Integer uploaded) {
        this.uploaded = uploaded;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
