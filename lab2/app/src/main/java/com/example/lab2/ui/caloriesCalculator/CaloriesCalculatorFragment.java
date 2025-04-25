package com.example.lab2.ui.caloriesCalculator;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab2.R;

import java.util.Objects;

public class CaloriesCalculatorFragment extends Fragment {

    private EditText ageInput, weightInput, heightInput;
    private RadioGroup genderGroup;
    private Spinner activitySpinner;
    private TextView resultText;
    private Button calculateButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_harris, container, false);

        findViews(rootView);

        setupClickListener();

        return rootView;
    }

    private void findViews(View view) {
        ageInput = view.findViewById(R.id.input_age);
        weightInput = view.findViewById(R.id.input_weight);
        heightInput = view.findViewById(R.id.input_height);
        genderGroup = view.findViewById(R.id.gender_group);
        activitySpinner = view.findViewById(R.id.activity_level_spinner);
        resultText = view.findViewById(R.id.result_text);
        calculateButton = view.findViewById(R.id.calculate_button);
    }

    private void setupClickListener() {
        calculateButton.setOnClickListener(v -> checkInputAndCalculate());
    }

    private void checkInputAndCalculate() {
        String ageString = ageInput.getText().toString().trim();
        String weightString = weightInput.getText().toString().trim();
        String heightString = heightInput.getText().toString().trim();

        if (ageString.isEmpty() || weightString.isEmpty() || heightString.isEmpty()) {
            resultText.setText(R.string.fieldsRequired);
            return;
        }

        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            resultText.setText(R.string.fieldsRequired);
            return;
        }

        int age = Integer.parseInt(ageString);
        double weight = Double.parseDouble(weightString);
        double height = Double.parseDouble(heightString);

        double bmr = calculateBMR(age, weight, height, selectedGenderId);

        double activityFactor = getActivityFactor();
        int caloricNeeds = (int) (bmr * activityFactor);

        showResult(caloricNeeds);

        hideKeyboard();
    }

    private double calculateBMR(int age, double weight, double height, int genderId) {
        RadioButton selectedGender = genderGroup.findViewById(genderId);
        boolean isMale = selectedGender.getId() == R.id.gender_male;

        if (isMale) {
            return 88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age);
        } else {
            return 447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age);
        }
    }

    private double getActivityFactor() {
        String activityLevel = activitySpinner.getSelectedItem().toString();
        String[] activityLevels = getResources().getStringArray(R.array.activity_levels);

        if (activityLevel.equals(activityLevels[0])) {
            return 1.2;
        } else if (activityLevel.equals(activityLevels[1])) {
            return 1.375;
        } else if (activityLevel.equals(activityLevels[2])) {
            return 1.55;
        } else if (activityLevel.equals(activityLevels[3])) {
            return 1.725;
        } else if (activityLevel.equals(activityLevels[4])) {
            return 1.9;
        } else {
            return 1.0;
        }
    }

    private void showResult(int calories) {
        String result = getString(R.string.harrisResult) + " " + calories + " " + getString(R.string.harrisUnit);
        resultText.setText(result);
    }

    private void hideKeyboard() {
        View view = Objects.requireNonNull(getActivity()).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}