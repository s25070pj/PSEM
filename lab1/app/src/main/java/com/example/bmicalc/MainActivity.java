package com.example.bmicalc;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText inputWeight;
    private EditText inputHeight;
    private Button submitButton;
    private TextView textResult;
    private String weight;
    private String height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputWeight = findViewById(R.id.inputWeight);
        inputHeight = findViewById(R.id.inputHeight);
        submitButton = findViewById(R.id.submit);
        textResult = findViewById(R.id.textResult);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight = inputWeight.getText().toString().trim();
                height = inputHeight.getText().toString().trim();

                if (isValidNumber(weight) || isValidNumber(height)) {
                    String resultText = getString(R.string.invalidInput);
                    textResult.setText(resultText);
                    return;
                }

                double bmi = calculateBMI(Integer.parseInt(weight), Integer.parseInt(height));

                String resultInfo = getString(R.string.result) + " " +
                        String.format(Locale.getDefault(), "%.2f", bmi) +
                        getString(R.string.itMeans) + " " +
                        getResultInfo(bmi);

                textResult.setText(resultInfo);
            }
        });
    }


    private String getResultInfo(double bmi) {
        if (bmi < 18.5) {
            return getString(R.string.underweight);
        } else if (bmi >= 18.5 && bmi < 25) {
            return getString(R.string.optimum);
        } else if (bmi >= 25 && bmi < 30) {
            return getString(R.string.overweight);
        } else {
            return getString(R.string.obese);
        }
    }

    private double calculateBMI(int weight, int height) {
        return (double) weight * 10000 / (height * height);
    }

    private boolean isValidNumber(String value) {
        try {
            int number = Integer.parseInt(value);
            return number < 1 || number > 300;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}