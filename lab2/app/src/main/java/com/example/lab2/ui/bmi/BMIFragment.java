package com.example.lab2.ui.bmi;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab2.R;
import com.example.lab2.databinding.FragmentBmiBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class BMIFragment extends Fragment {
    private FragmentBmiBinding binding;
    private LineChart bmiChart;
    private ArrayList<Entry> bmiEntries;
    private ArrayList<String> dates;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBmiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bmiChart = binding.bmiChart;
        binding.chartContainer.setVisibility(View.GONE);

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

            generateBmiChartData(bmiResult);
            binding.chartContainer.setVisibility(View.VISIBLE);

            closeKeyboard(view1);
        });
    }

    boolean checkInput(String input) {
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

    private void generateBmiChartData(double currentBmi) {
        bmiEntries = new ArrayList<>();
        dates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM", new Locale("pl"));

        float smallDecrement = 0.3f;

        Random random = new Random();

        for (int i = 1; i <= 5; i++) {
            calendar.add(Calendar.MONTH, -1);
            Date date = calendar.getTime();
            dates.add(sdf.format(date));

            float historicalBmi = (float)currentBmi + (smallDecrement * i) + (random.nextFloat() * 0.4f) - 0.2f;;
            bmiEntries.add(new Entry(i, historicalBmi));
        }

        ArrayList<Entry> chronologicalEntries = new ArrayList<>();
        ArrayList<String> chronologicalDates = new ArrayList<>();

        for (int i = bmiEntries.size() - 1; i >= 0; i--) {
            Entry entry = bmiEntries.get(i);
            chronologicalEntries.add(new Entry(bmiEntries.size() - 1 - i, entry.getY()));
            chronologicalDates.add(dates.get(i));
        }

        bmiEntries = chronologicalEntries;
        dates = chronologicalDates;

        setupChart();
    }

    private void setupChart() {
        LineDataSet dataSet = new LineDataSet(bmiEntries,"");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setValueTextSize(12f);

        LineData lineData = new LineData(dataSet);
        bmiChart.setData(lineData);

        XAxis xAxis = bmiChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        bmiChart.animateX(1500);
        bmiChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}