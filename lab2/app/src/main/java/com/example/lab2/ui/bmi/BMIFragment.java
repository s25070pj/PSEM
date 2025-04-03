package com.example.lab2.ui.bmi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab2.R;
import com.example.lab2.databinding.FragmentBmiBinding;

import java.util.Locale;

public class BMIFragment extends Fragment {
    private FragmentBmiBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBmiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.submit.setOnClickListener(view1 -> {
            String weight = binding.inputWeight.getText().toString().trim();
            String height = binding.inputHeight.getText().toString().trim();

            if (!checkInput(weight) || !checkInput(height)) {
                binding.textResult.setText(getString(R.string.invalidInput));
                return;
            }

            double weightValue = Double.parseDouble(weight);
            double heightValue = Double.parseDouble(height);

            double bmiResult = weightValue / ((heightValue / 100.0) * (heightValue / 100.0));

            String resultMessage = getString(R.string.result) + " "
                    + String.format(Locale.getDefault(), "%.2f", bmiResult)
                     + ": "
                    + getBmiCategory(bmiResult);

            binding.textResult.setText(resultMessage);

            closeKeyboard(view1);
        });
    }

    private boolean checkInput(String input) {
        try {
            double value = Double.parseDouble(input);
            return value > 0 && value <= 300;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String getBmiCategory(double bmi) {
        if (bmi < 18.5) {
            return getString(R.string.underweight);
        } else if (bmi < 25) {
            return getString(R.string.optimum);
        } else if (bmi < 30) {
            return getString(R.string.overweight);
        } else {
            return getString(R.string.obese);
        }
    }

    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}