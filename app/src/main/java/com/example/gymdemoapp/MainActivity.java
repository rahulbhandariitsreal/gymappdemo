package com.example.gymdemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymdemoapp.DAO.User_DAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Button button1, button2;
    String user_name;

    String my_bmi;

    private DBHelper db;
    private User_DAO user_dao;

    private TextView userName_edit,Bmi_user_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        userName_edit=toolbar.findViewById(R.id.username_edittext);
        Bmi_user_edit=toolbar.findViewById(R.id.userbmi_edittext);

        button1 = findViewById(R.id.start_yoga1);
        button2 = findViewById(R.id.start_yoga2);
        user_name = getIntent().getStringExtra("user_name");
        db = Room.databaseBuilder(getApplicationContext(),
                DBHelper.class, "database-name").build();


        userName_edit.setText(user_name);


        user_dao = db.user_dao();

        String action = getIntent().getAction();

        switch (action) {
            case "newuser":
                calculate();
                break;

            case "olduser":
                getbmi();
                break;
        }


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity2.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, secondActivity3.class);
                startActivity(intent);

            }
        });
    }

    private void getbmi() {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                my_bmi = user_dao.findbmi(user_name);
            }
        });

        Bmi_user_edit.setText("BMI: "+my_bmi);

    }

    private void calculate() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("BMI Calculator");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.bmi_calculator, null);
        dialogBuilder.setView(dialogView);

        final EditText heightEditText = dialogView.findViewById(R.id.height_edittext);
        final EditText weightEditText = dialogView.findViewById(R.id.weight_edittext);

        dialogBuilder.setPositiveButton("Calculate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String heightString = heightEditText.getText().toString();
                String weightString = weightEditText.getText().toString();

                if (!heightString.isEmpty() && !weightString.isEmpty()) {
                    double height = Double.parseDouble(heightString);
                    double weight = Double.parseDouble(weightString);

                    double bmi = calculateBmi(height, weight);
                    String bmiResult = String.format("%.2f", bmi);
                    my_bmi=bmiResult;
                    Bmi_user_edit.setText("BMI: "+bmiResult);
                    ExecutorService executorService=Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            user_dao.update_bmi(user_name,bmiResult);
                        }
                    });

                    displayBmiResult(bmiResult);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter valid height and weight", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


    public double calculateBmi(double height, double weight) {
        double heightInMeters = height / 100;
        return weight / (heightInMeters * heightInMeters);
    }

    public void displayBmiResult(String bmiResult) {
        Toast.makeText(this, "Your BMI is: " + bmiResult, Toast.LENGTH_SHORT).show();
    }

    public void beforage18(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity2.class);
        startActivity(intent);
    }

    public void afterage18(View view) {
        Intent intent = new Intent(MainActivity.this, secondActivity3.class);
        startActivity(intent);
    }

    public void food(View view) {

        Intent intent = new Intent(MainActivity.this, FoodActivity.class);
        startActivity(intent);

    }
}