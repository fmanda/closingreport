package com.fma.closingrepclient.model;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fma on 8/11/2017.
 */

public class ModelSetting extends BaseModel {
    @TableField
    private String varname;
    @TableField
    private String varvalue;

    public ModelSetting(String varname, String varvalue){
        this.varname = varname;
        this.varvalue = varvalue;
    }

    public ModelSetting() {
        this.varname = "";
        this.varvalue = "";
    }

    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    public String getVarvalue() {
        return varvalue;
    }

    public void setVarvalue(String varvalue) {
        this.varvalue = varvalue;
    }

    public static void initMetaData(SQLiteDatabase db) {
        //company_info
        new ModelSetting("company_name","[your-company-name]").saveToDB(db);
        new ModelSetting("company_address","[your company address]\n[your company address]").saveToDB(db);
        new ModelSetting("company_phone","[company-phone]").saveToDB(db);
        new ModelSetting("rest_url","127.0.0.1").saveToDB(db);

    }
}
