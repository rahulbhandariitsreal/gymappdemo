package com.example.gymdemoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

public class SecondActivity2 extends AppCompatActivity {

    int[] newArray;
   

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        androidx.appcompat.widget.Toolbar toolbar =findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        newArray = new int[]{
            R.id.boat_pose1,R.id.boat_pose2,R.id.boat_pose3,R.id.boat_pose4,R.id.boat_pose5,R.id.boat_pose6,R.id.boat_pose7,R.id.boat_pose8,R.id.boat_pose9,R.id.boat_pose10,R.id.boat_pose11,R.id.boat_pose12,R.id.boat_pose13,R.id.boat_pose14,R.id.boat_pose15

        };
    }


    public void Imagebuttonclicked(View view) {

        for (int i=0;i<newArray.length;i++){

            if(view.getId() == newArray[i]){
                int value = i+1;
                Log.i("FIRST",String.valueOf(value));
                Intent intent = new Intent(SecondActivity2.this,ThirdActivity.class);
                intent.putExtra("value",String.valueOf(value));
                startActivity(intent);
            }
        }
    }
}