package com.fma.closingrepclient.model;


/**
 * Created by fma on 8/11/2017.
 */

public class ModelUser extends BaseModel {
    @TableField
    private String uid;
    @TableField
    private String username;
    @TableField
    private String nama;
    @TableField
    private String password;
    @TableField
    private String role;
    @TableField
    private Integer area_id;

    public ModelUser() {
    }

    @Override
    public String getTableName() {
        return "users";
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }
}
