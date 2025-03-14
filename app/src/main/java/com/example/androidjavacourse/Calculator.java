package com.example.androidjavacourse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Calculator extends AppCompatActivity {

    public static final String TAG ="Calculator";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Buttons
        Button addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "add clicked");
                calculate(1);
            }
        });

        Button subBtn = (Button) findViewById(R.id.subBtn);
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "sub clicked");
                calculate(2);
            }
        });

        Button multBtn = (Button) findViewById(R.id.multBtn);
        multBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mult clicked");
                calculate(3);
            }
        });

        Button divBtn = (Button) findViewById(R.id.divBtn);
        divBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "div clicked");
                calculate(4);
            }
        });
    }

    public void calculate(int btnIndex){
        // btnIndex 1 = add, 2 = sub, 3 = mult, 4 = div

        // Fields
        TextInputEditText firstNumber = (TextInputEditText) findViewById(R.id.firstNumberField);
        TextInputEditText secondNumber = (TextInputEditText) findViewById(R.id.secondNumberField);
        TextInputEditText totalField = (TextInputEditText) findViewById(R.id.resultField);

        // Variables
        Double total = 0.0;
        Double firstNb = 0.0;
        Double secondNb = 0.0;
        Boolean canCalc = true;

        // Get numbers
        try{
            firstNb = Double.parseDouble(firstNumber.getText().toString());
            secondNb = Double.parseDouble(secondNumber.getText().toString());
        } catch (Exception e) {
            canCalc = false;
        }

        // Calculate
        if (canCalc){
        switch (btnIndex){
            case 1:
                total = firstNb + secondNb;
                totalField.setText(total.toString());
                break;
            case 2:
                total = firstNb - secondNb;
                totalField.setText(total.toString());
                break;
            case 3:
                total = firstNb * secondNb;
                totalField.setText(total.toString());
                break;
            case 4:
                total = firstNb / secondNb;
                totalField.setText(total.toString());
                break;
        }}
    }
}