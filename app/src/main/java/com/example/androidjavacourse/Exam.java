package com.example.androidjavacourse;

import static java.lang.StrictMath.round;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Exam extends AppCompatActivity {

    public static final String TAG ="Exam";
    double sekToEur = 0.091;
    double nokToEur = 0.085;
    double dkkToEur = 0.130;
    int previousClick = 0;
    MaterialButtonToggleGroup materialButtonToggleGroup;
    TextInputLayout valueBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exam);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button group
        materialButtonToggleGroup = (MaterialButtonToggleGroup) findViewById(R.id.toggleBtnGroup);
        materialButtonToggleGroup.addOnButtonCheckedListener(
                new MaterialButtonToggleGroup.OnButtonCheckedListener() {
                    @Override
                    public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                        if (isChecked) {
                            if (checkedId == R.id.btn_sek) {
                                valueBase.setHint("SEK");
                                previousClick = 1;
                            } else if (checkedId == R.id.btn_nok) {
                                valueBase.setHint("NOK");
                                previousClick = 2;
                            } else if (checkedId == R.id.btn_dkk) {
                                valueBase.setHint("DKK");
                                previousClick = 3;
                            }
                            Log.d(TAG, "Clicked group");
                            calculate();
                        }
                        else {
                            Log.d(TAG, "Double click? " + previousClick);
                            calculate();
                        }
                    }
                });

        // input field base
        valueBase = (TextInputLayout) findViewById(R.id.numberFieldBase);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.examToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.exam_toolbar));
    }
    public void calculate(){
        TextInputEditText value = (TextInputEditText) findViewById(R.id.numberField);
        TextView output = (TextView) findViewById(R.id.outputText);

        Double number = 0.0;
        Integer previousClickSelector = previousClick;

        // Disable error
        valueBase.setErrorEnabled(false);

        // Try to convert field value as a number
        try{
            number = Double.parseDouble(value.getText().toString());
            Log.d(TAG, number.toString());
        } catch (Exception e) {
            Log.e(TAG, "Cannot convert value");
            previousClickSelector = -1;
        }

        // Switch to select shown text and in case of number calculate conversion
        // 1 sek , 2 nok , 3 dkk , -1 error // Selector values
        switch(previousClickSelector) {
            case 1:
                number = number * sekToEur;
                output.setText(getTextFromNumber(number));
                break;
            case 2:
                number = number * nokToEur;
                output.setText(getTextFromNumber(number));
                break;
            case 3:
                number = number * dkkToEur;
                output.setText(getTextFromNumber(number));
                break;
            case -1:
                output.setText("0 €");
                valueBase.setErrorEnabled(true);
                valueBase.setError(getResources().getString(R.string.exam_error));
                break;
            default:
                valueBase.setHint(getResources().getString(R.string.exam_startingHint));
                output.setText("0.000 €");
                break;
        }

    };

    public String getTextFromNumber(Double number){
        // Round number to 3 decimals
        Long number2 = round(number * 1000);
        number = number2.doubleValue() / 1000;

        // Return number as a string and add symbol "€" at the end
        return number.toString() + " €";
    };

}