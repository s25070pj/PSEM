package com.example.lab2.ui.shopping;
public class ShoppingItem {
    private String name;
    private boolean purchased;
    private String recipeCategory;

    public ShoppingItem(String name, String recipeCategory) {
        this.name = name;
        this.purchased = false;
        this.recipeCategory = recipeCategory;
    }

    public String getName() {
        return name;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String getRecipeCategory() {
        return recipeCategory;
    }
}