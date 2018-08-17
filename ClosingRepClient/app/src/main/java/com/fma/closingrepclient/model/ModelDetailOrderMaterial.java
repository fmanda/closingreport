package com.fma.closingrepclient.model;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fma on 8/11/2017.
 */

public class ModelDetailOrderMaterial extends BaseModel {
    @Override
    public String getTableName() {
        return "detailordermaterial";
    }

    @TableField
    private Integer detailorder_id;

    @TableField
    private Integer material_id;
    private String material_uid;

    public ModelMaterial material;
    public void loadMaterial(SQLiteDatabase db) {
        material = new ModelMaterial();
        if (this.material_id != null)
            material.loadFromDB(db, this.material_id);
    }

    @TableField
    private Double qty = 0.0;

    @TableField
    private String serialnumber;

    public Integer getDetailorder_id() {
        return detailorder_id;
    }

    public void setDetailorder_id(Integer detailorder_id) {
        this.detailorder_id = detailorder_id;
    }

    public Integer getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(Integer material_id) {
        this.material_id = material_id;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public void prepareUpload(SQLiteDatabase db) {
        if (material_id > 0) {
            ModelMaterial material = new ModelMaterial();
            material.loadFromDB(db, this.material_id);
            this.material_uid = material.getUid();
        }
    }
}
