package com.example.app_dev_money_tracking;

import java.util.ArrayList;

public class Categories {
    private String categoryName;
    private int categoryImg;

    public Categories() {}

    public Categories(String categoryName, int categoryImg) {
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(int categoryImg) {
        this.categoryImg = categoryImg;
    }

    static public ArrayList<Categories> getData() {
        ArrayList<Categories> categoriesList = new ArrayList<Categories>();
        String[] categories = {"Food & Drinks", "Shopping", "Housing", "Transportation", "Vehicle", "Entertainment", "Medical",
                "Investments", };
        int[] images = {R.drawable.cutlery, R.drawable.shoppingbag, R.drawable.home, R.drawable.bus,
                R.drawable.car, R.drawable.entertainment, R.drawable.healthinsurance, R.drawable.investment};

        for(int i=0; i<categories.length; i++) {
            Categories category = new Categories();
            category.setCategoryName(categories[i]);
            category.setCategoryImg(images[i]);

            categoriesList.add(category);
        }
        return categoriesList;
    }
}
