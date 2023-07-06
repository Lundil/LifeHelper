package com.example.myapplicationtest.entity.food;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class UberEat {

    private Long id;

    private String name;

    private List<Meal> listMeal;

    public UberEat() {
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

    public List<Meal> getListMeal() {
        return listMeal;
    }

    public void setListMeal(List<Meal> listMeal) {
        this.listMeal = listMeal;
    }

}
