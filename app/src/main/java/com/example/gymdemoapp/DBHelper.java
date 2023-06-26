package com.example.gymdemoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gymdemoapp.DAO.User_DAO;
import com.example.gymdemoapp.model.User;


@Database(entities = {User.class},version = 1)
public abstract class DBHelper extends RoomDatabase {
    public abstract User_DAO user_dao();

//    public DBHelper(Context context) {
//        super(context,"login.db", null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table users(username Text primary key, password Text)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("drop table if exists user");
//    }
//
//    public Boolean insertData(String username, String password){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("username",username);
//        values.put("password",password);
//
//        long result = db.insert("users",null, values);
//        if(result==-1) {
//            return false;
//        }
//        else {
//            return true;
//        }
//    }
//    public Boolean checkusername(String username){
//        SQLiteDatabase db=this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from users where username= ?",new String[] {username});
//
//        if(cursor.getCount()>0) {
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
//    public Boolean checkusernamepassword(String username, String password){
//        SQLiteDatabase db=this.getWritableDatabase();
//        Cursor cursor =  db.rawQuery("select * from users where username=? and password=?",new String[] {username,password});
//
//        if (cursor.getCount()>0) {
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
}
