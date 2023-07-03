package com.example.myapplicationtest.entity;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class ShoppingList {

    public String id;
    public List<String> things;

    public ShoppingList() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ShoppingList(String id, List<String> things) {
        this.id = id;
        this.things = things;
    }

}