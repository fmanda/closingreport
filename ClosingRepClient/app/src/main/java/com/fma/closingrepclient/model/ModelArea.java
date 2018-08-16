package com.fma.closingrepclient.model;


import android.database.sqlite.SQLiteDatabase;

import com.fma.closingrepclient.helper.DBHelper; /**
 * Created by fma on 8/11/2017.
 */

public class ModelArea extends BaseModel {
    @TableField
    private String uid;

    @TableField
    private String kode;

    @TableField
    private String nama;

    public ModelArea(){
        super();
    }

    public ModelArea(SQLiteDatabase readableDatabase, String area_uid) {
        super(readableDatabase, area_uid);
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
