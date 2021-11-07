package com.example.app_dev_money_tracking;

public class Category {
    int cat_id;
    String Name;
    int Image;

    public Category(int id, String name, int image) {
        cat_id = id;
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
