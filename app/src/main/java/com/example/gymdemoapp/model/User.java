package com.example.gymdemoapp.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user",indices = {@Index(value = {"user_name"},
        unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    private int u_id;

    @ColumnInfo(name = "user_name")
    private String user_name;

    @ColumnInfo(name = "user_password")
    private String user_password;

    @ColumnInfo(name = "bmi")
    private String bmi;

    public User() {
    }


    public User(String user_name, String user_password, String bmi) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.bmi = bmi;
    }

    public User(String user_name, String user_password) {
        this.user_name = user_name;
        this.user_password = user_password;
    }

    public User(int u_id, String user_name, String user_password) {
        this.u_id = u_id;
        this.user_name = user_name;
        this.user_password = user_password;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
