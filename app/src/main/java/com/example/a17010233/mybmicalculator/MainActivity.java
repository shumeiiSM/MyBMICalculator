package com.example.a17010233.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button bCalculate;
    Button bReset;
    TextView tvCal;
    TextView tvBMI;
    TextView tvWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        bCalculate = findViewById(R.id.calculateButton);
        bReset = findViewById(R.id.resetButton);
        tvCal = findViewById(R.id.displayDateTime);
        tvBMI = findViewById(R.id.displayBMI);
        tvWeight = findViewById(R.id.displayWeight);



        bCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etWeight.requestFocus();
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                float cal = weight / (height * height);

                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH) + 1 ) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);


                tvCal.setText("Last Calculated Date:" + datetime);
                tvBMI.setText("Last Calculated BMI:" + String.valueOf(cal));
                etWeight.setText("");
                etHeight.setText("");

                if (cal < 18.5) {
                    tvWeight.setText("You are underweight");
                } else if (cal < 25) {
                    tvWeight.setText("Your BMI is normal");
                } else if(cal < 30) {
                    tvWeight.setText("You are overweight");
                } else {
                    tvWeight.setText("You are obese");
                }
            }

        });

        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCal.setText("Last Calculated Date:");
                tvBMI.setText("Last Calculated BMI:");
                tvWeight.setText("");
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).
                        edit().clear().commit();

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        float weight=0;
        float height=0;

        if (!etWeight.getText().toString().isEmpty()) {
            //Step 1a: Get the user input from the EditText and store it in a variable
            weight = Float.parseFloat(etWeight.getText().toString());
            height = Float.parseFloat(etHeight.getText().toString());


            float cal = weight / (height * height);

            Calendar now = Calendar.getInstance();
            String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                    (now.get(Calendar.MONTH) + 1 ) + "/" +
                    now.get(Calendar.YEAR) + " " +
                    now.get(Calendar.HOUR_OF_DAY) + ":" +
                    now.get(Calendar.MINUTE);

            //Step 1b: Obtain an instance of the SharedPreferences
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

            //Step 1c: Obtain an instance of the SharedPreferences Editor for update later
            SharedPreferences.Editor prefEdit = pref.edit();

            //Step 1d: Add the key-value pair
            //         The value should be from the variable defined in Step 1a
            prefEdit.putString("myDate", datetime);


            prefEdit.putFloat("myBMI", cal);

            //Step 1e: Call commit() method to save the changes into SharedPreferences
            prefEdit.commit();
        }
        else {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

            //Step 1c: Obtain an instance of the SharedPreferences Editor for update later
            SharedPreferences.Editor prefEdit = pref.edit();

            //Step 1d: Add the key-value pair
            //         The value should be from the variable defined in Step 1


            prefEdit.putFloat("myBMI", 0);

            //Step 1e: Call commit() method to save the changes into SharedPreferences
            prefEdit.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data from the SharedPreferences object
        String rDate = pref.getString("myDate", "");
        float rBMI = pref.getFloat("myBMI", 0.0f);

        //Step 2c: Update the UI element with the value
        tvCal.setText("Last Calculated Date:" + rDate);
        tvBMI.setText("Last Calculated BMI:" + String.valueOf(rBMI));

    }

}