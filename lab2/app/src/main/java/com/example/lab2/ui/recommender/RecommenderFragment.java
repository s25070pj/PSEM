package com.example.lab2.ui.recommender;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab2.R;
import com.example.lab2.databinding.FragmentRecommenderBinding;

public class RecommenderFragment extends Fragment {

    private FragmentRecommenderBinding binding;
    private EditText caloriesInput;
    private Button calculateBtn;
    private TextView resultsView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecommenderBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        initializeViews();

        setupButtonListener();

        return rootView;
    }

    private void initializeViews() {
        caloriesInput = binding.inputCaloricNeeds;
        calculateBtn = binding.buttonCalculateRecommendations;
        resultsView = binding.textDashboard;
    }

    private void setupButtonListener() {
        calculateBtn.setOnClickListener(view -> processUserInput());
    }

    private void processUserInput() {
        String caloriesText = caloriesInput.getText().toString().trim();

        if (caloriesText.isEmpty()) {
            resultsView.setText(getString(R.string.enter_caloric_needs));
            return;
        }

        int calories = Integer.parseInt(caloriesText);

        String dietRecommendations = selectDietRecommendations(calories);

        displayResults(dietRecommendations);

        clearFocusAndHideKeyboard();
    }

    private String selectDietRecommendations(int calories) {
        if (calories <= 1600) {
            return getString(R.string.low_calorie_recommendations);
        } else if (calories <= 2600) {
            return getString(R.string.balanced_diet_recommendations);
        } else {
            return getString(R.string.high_calorie_recommendations);
        }
    }

    private void displayResults(String recommendations) {
        resultsView.setText(recommendations);
    }

    private void clearFocusAndHideKeyboard() {
        caloriesInput.clearFocus();

        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(caloriesInput.getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}