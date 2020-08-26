package com.example.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registration.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

public class UserTime extends AppCompatActivity  {

    //Button Strbtn,stopbtn;
    // Spinner spinner;
    TextView payTest;
    String x= "x";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        payTest = findViewById(R.id.pay);
        List<Double> categories = new ArrayList<Double>();
        categories.add(0.0);
        categories.add(1.0);
        categories.add(2.0);
        categories.add(3.0);
        categories.add(4.0);
        categories.add(5.0);
        categories.add(6.0);

        Spinner spinner=findViewById(R.id.spinner1);
        // Creating adapter for spinner
        ArrayAdapter<Double> dataAdapter = new ArrayAdapter<Double>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    payTest.setText("make a Selection");
                }
                else {


                    String item = adapterView.getItemAtPosition(i).toString();
                    Double hrs = parseDouble(item);
                    double markertitle = (hrs * 5) + (hrs * .13) + 3;
                    payTest.setText(String.format("%f", markertitle));
                    Intent in = new Intent (UserTime.this,BillingActivity.class);
                    in.putExtra("title",payTest.getText());
                    startActivity(in);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}

