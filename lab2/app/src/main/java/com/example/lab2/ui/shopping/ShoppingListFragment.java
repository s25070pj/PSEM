package com.example.lab2.ui.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoppingListFragment extends Fragment implements ShoppingListAdapter.OnItemClickListener {

    private List<ShoppingItem> shoppingItems;
    private ShoppingListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.shopping_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        shoppingItems = extractShoppingItemsFromResources();

        adapter = new ShoppingListAdapter(shoppingItems, this);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onItemClick(int position) {
        ShoppingItem item = shoppingItems.get(position);
        item.setPurchased(!item.isPurchased());
        adapter.notifyItemChanged(position);
    }

    private List<ShoppingItem> extractShoppingItemsFromResources() {
        List<ShoppingItem> items = new ArrayList<>();

        String[] recipesResources = {
                getString(R.string.low_calorie_recommendations),
                getString(R.string.balanced_diet_recommendations),
                getString(R.string.high_calorie_recommendations)
        };

        for (String recipeResource : recipesResources) {
            extractRecipesAndIngredients(recipeResource, items);
        }

        return items;
    }

    private void extractRecipesAndIngredients(String recipeText, List<ShoppingItem> items) {

        Pattern recipePattern = Pattern.compile("\\d+\\.\\s(.*?)\\s\\(\\d+\\s*kcal\\)[\\s\\S]*?Składniki:\\s(.*?)\\.");
        Matcher recipeMatcher = recipePattern.matcher(recipeText);

        while (recipeMatcher.find()) {
            String recipeName = recipeMatcher.group(1).trim();
            String ingredientsText = recipeMatcher.group(2).trim();

            // Rozdzielenie składników (oddzielonych przecinkami)
            String[] ingredients = ingredientsText.split(",\\s*");

            // Dodanie każdego składnika do listy zakupów
            for (String ingredient : ingredients) {
                items.add(new ShoppingItem(ingredient.trim(), recipeName));
            }
        }
    }
}