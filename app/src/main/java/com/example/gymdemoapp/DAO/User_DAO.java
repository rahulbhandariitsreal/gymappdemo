package com.example.gymdemoapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymdemoapp.model.User;

import java.util.List;



@Dao
public interface User_DAO {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE u_id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE user_name LIKE :name AND user_password LIKE :pass LIMIT 1" )
    boolean findByName(String name, String pass);

    @Query("SELECT * FROM user WHERE user_name LIKE :name  LIMIT 1" )
    boolean findByName(String name);

    @Query("SELECT bmi FROM user WHERE user_name LIKE :name  LIMIT 1" )
    String findbmi(String name);

    @Query("UPDATE user SET user_password = :end_address WHERE user_name = :tid")
    int update_password(String tid, String end_address);

    @Query("UPDATE user SET bmi = :bmi WHERE user_name = :name")
    int update_bmi(String name, String bmi);


    @Insert
    long insert_user(User user);

    @Delete
    void delete(User user);

}
