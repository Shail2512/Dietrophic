package com.trial.dietrophic;

import java.io.Serializable;

public class Eat implements Serializable {
    String food;
    String calories;
    String fat;
    String protein;

    public Eat() {
    }

    public Eat(String food, String calories, String fat, String protein) {
        this.food = food;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
    };

    @Override
    public String toString() {
        return food+","+calories+","+fat+","+protein;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }
}
