package com.example.gymdemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymdemoapp.DAO.User_DAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button signin;
    DBHelper DB;

    private User_DAO user_dao;

    private  boolean have_user;
    private TextView textView;

    private ExecutorService executorService;

    private  boolean checkuserpass;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username1);
        password=findViewById(R.id.password1);
        signin=findViewById(R.id.signin1);

        textView=findViewById(R.id.forget_pass);

        executorService= Executors.newSingleThreadExecutor();


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserpasswrod();
            }
        });

         DB = Room.databaseBuilder(getApplicationContext(),
                DBHelper.class, "database-name").build();

         user_dao=DB.user_dao();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user=username.getText().toString();
                String pass=password.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
                    Toast.makeText(LoginActivity.this,"All fields Required",Toast.LENGTH_SHORT).show();
                else{
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                             checkuserpass= user_dao.findByName(user,pass);
                        }
                    });


                    if(checkuserpass){
                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void gotosingup(View view) {
        startActivity(new Intent(LoginActivity.this,Splashscreenok.class));
        finish();
    }

    public void reserpasswrod() {
        String user=username.getText().toString();

        if(TextUtils.isEmpty(user))
            Toast.makeText(LoginActivity.this,"Enter Username",Toast.LENGTH_SHORT).show();
        else
        {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                     have_user=user_dao.findByName(user);
                }
            });

         if(have_user){
             showCustomDialog();
         }else{
             Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
         }
        }


    }

    private void showCustomDialog() {
        // Create an AlertDialog Builder
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);

        // Get the layout inflater
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout for the dialog
        View dialogView = inflater.inflate(R.layout.change_password_layout, null);


         EditText usernameEditText, newPasswordEditText, confirmPasswordEditText;
         Button changePasswordButton;


         dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
             }
         });

        usernameEditText = dialogView.findViewById(R.id.username_edittext);
        newPasswordEditText = dialogView.findViewById(R.id.new_password_edittext);
        confirmPasswordEditText = dialogView.findViewById(R.id.confirm_password_edittext);
        changePasswordButton = dialogView.findViewById(R.id.change_password_button);


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }

            private void changePassword() {
                // Retrieve entered values
                String username = usernameEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // Perform password change validation
                if (username.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: Perform password change logic
                    Toast.makeText(getApplicationContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();


                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            user_dao.update_password(username,newPassword);
                        }
                    });
                    // Clear the fields after successful password change
                    usernameEditText.setText("");
                    newPasswordEditText.setText("");
                    confirmPasswordEditText.setText("");
                }
            }
        });

        // Set the custom view to the dialog builder
        dialogBuilder.setView(dialogView);

        // Create and show the AlertDialog
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}