package com.trial.dietrophic;

public class GetStartModel {
    String name;
    int Weight;
    int Height;

    public GetStartModel(String name, int Weight, int Height) {
        this.name= name;
        this.Weight = Weight;
        this.Height = Height;
    }

    public GetStartModel() {
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int Weight) {
        this.Weight = Weight;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int Height) {
        this.Height = Height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }
}
