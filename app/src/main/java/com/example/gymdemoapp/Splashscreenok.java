package com.example.gymdemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymdemoapp.DAO.User_DAO;
import com.example.gymdemoapp.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Splashscreenok extends AppCompatActivity {

    EditText username,password,repassword;
    Button signup,signin;
    DBHelper DB;

    private User_DAO user_dao;

    private ExecutorService executorService;

    private  boolean checkuser;
    private   long insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreenok);

        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        repassword= (EditText) findViewById(R.id.repassword);

        signup= (Button) findViewById(R.id.signup);
        signin= (Button) findViewById(R.id.signin);

        DB = Room.databaseBuilder(getApplicationContext(),
                DBHelper.class, "database-name").build();

        executorService= Executors.newSingleThreadExecutor();

        user_dao=DB.user_dao();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user= username.getText().toString();
                String pass= password.getText().toString();
                String repass= repassword.getText().toString();
                User user1=new User(user,pass);

                if(user.equals("") || pass.equals("") || repass.equals(""))
                    Toast.makeText(Splashscreenok.this,"all finds Required",Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                 checkuser = user_dao.findByName(user);
                            }
                        });

                        if(!checkuser){

                            ExecutorService executorService1=Executors.newSingleThreadExecutor();
                            executorService1.execute(new Runnable() {
                                @Override
                                public void run() {
                                    insert = user_dao.insert_user(user1);
                                }
                            });


                            if(insert>=0){
                                Toast.makeText(Splashscreenok.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("user_name",user);
                                intent.setAction("newuser");
                                startActivity(intent);
                            }else{
                                Toast.makeText(Splashscreenok.this,"Registration failed",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Splashscreenok.this,"User already Exists",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Splashscreenok.this,"Password are not matching",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}