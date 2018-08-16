package com.fma.closingrepclient.model;


import android.database.sqlite.SQLiteDatabase; /**
 * Created by fma on 8/11/2017.
 */

public class ModelProduct extends BaseModel {
    @TableField
    private String uid;

    @TableField
    private String kode;

    @TableField
    private String nama;

    public ModelProduct(){
        super();
    }
    public ModelProduct(SQLiteDatabase readableDatabase, String product_uid) {
        super(readableDatabase,product_uid);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

}
