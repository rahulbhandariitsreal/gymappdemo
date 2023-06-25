package com.example.gymdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {
    String buttonvalue;
    Button start_but;
    private CountDownTimer countDownTimer;
    TextView mtext_view;
    private boolean MTimeRunning;
    private long MTimeLeftmills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        Intent intent = getIntent();
        buttonvalue = intent.getStringExtra("value");

        int int_value = Integer.valueOf(buttonvalue);

        switch (int_value){
            case 1:
                setContentView(R.layout.boat_pose1);
                break;

            case 2:
                setContentView(R.layout.boat_pose2);
                break;
            case 3:
                setContentView(R.layout.boat_pose3);
                break;
            case 4:
                setContentView(R.layout.boat_pose4);
                break;
            case 5:
                setContentView(R.layout.boat_pose5);
                break;
            case 6:
                setContentView(R.layout.boat_pose6);
                break;
            case 7:
                setContentView(R.layout.boat_pose7);
                break;
            case 8:
                setContentView(R.layout.boat_pose8);
                break;
            case 9:
                setContentView(R.layout.boat_pose9);
                break;
            case 10:
                setContentView(R.layout.boat_pose10);
                break;
            case 11:
                setContentView(R.layout.boat_pose11);
                break;
            case 12:
                setContentView(R.layout.boat_pose12);
                break;
            case 13:
                setContentView(R.layout.boat_pose13);
                break;
            case 14:
                setContentView(R.layout.boat_pose14);
                break;
            case 15:
                setContentView(R.layout.boat_pose15);
                break;
        }

        start_but = findViewById(R.id.startbutton);
        mtext_view = findViewById(R.id.time);


        start_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MTimeRunning){
                    stop_Timer();
                }
                else
                {
                    start_Timer();
                }
            }
        });
    }
    private void stop_Timer(){

        countDownTimer.cancel();
        MTimeRunning=false;
        start_but.setText("START");
    }

    private void start_Timer(){
        final CharSequence value1 = mtext_view.getText();
        String num1 = value1.toString();
        String num2 = num1.substring(0,2);
        String num3 = num1.substring(3,5);


        final int number = Integer.valueOf(num2) * 60+ Integer.valueOf(num3);
        MTimeLeftmills = number*1000;

        countDownTimer = new CountDownTimer(MTimeLeftmills,1000) {
            @Override
            public void onTick(long milliUntilFinished) {

                MTimeLeftmills = milliUntilFinished;
                updateTimer();


            }

            @Override
            public void onFinish() {

                int newvalue = Integer.valueOf(String.valueOf(buttonvalue))+1;
                if(newvalue<=7){
                    Intent intent = new Intent(ThirdActivity.this,ThirdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newvalue));
                    startActivity(intent);
                }
                else {

                    newvalue = 1;
                    Intent intent = new Intent(ThirdActivity.this,ThirdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value", String.valueOf(newvalue));
                    startActivity(intent);
                }

            }
        }.start();
        start_but.setText("Pause");
        MTimeRunning=true;
    }
    private void updateTimer(){
        int minutes = (int) MTimeLeftmills/60000;
        int seconds = (int) MTimeLeftmills%60000 /1000;

        String timeLeftText="";
        if (minutes<10)
            timeLeftText="0";
        timeLeftText = timeLeftText+minutes+":";
        if (seconds<10)
            timeLeftText="0";
        timeLeftText+=seconds;
        mtext_view.setText(timeLeftText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}