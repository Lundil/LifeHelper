package com.example.myapplicationtest.entity.food;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Ingredient {

    private Long id;

    private String name;

    private String type;

    private Long proteins;

    private Long carbs;

    private Long fat;

    public Ingredient() {
        // Default constructor required for calls to DataSnapshot.getValue(Ingredient.class)
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getProteins() {
        return proteins;
    }

    public void setProteins(Long proteins) {
        this.proteins = proteins;
    }

    public Long getCarbs() {
        return carbs;
    }

    public void setCarbs(Long carbs) {
        this.carbs = carbs;
    }

    public Long getFat() {
        return fat;
    }

    public void setFat(Long fat) {
        this.fat = fat;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name=" + name + "\'" +
                ", type=" + type + "\'" +
                ", proteins=" + proteins +
                ", carbs=" + carbs +
                ", fat=" + fat +
                '}';
    }
}
