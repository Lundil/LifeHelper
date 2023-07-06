package com.example.myapplicationtest.factory;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplicationtest.entity.food.Ingredient;
import com.example.myapplicationtest.utils.Infos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FoodFactory {

    private DatabaseReference mDatabase;

    private Context context;
    private Infos infos;

    public FoodFactory(Context context) {
        this.context = context;
    }

    public void writeNewIngredient(String name, String type, Long proteins, Long carbs, Long fat) {
        infos = infos.getInstance();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName(name);
        ingredient.setProteins(proteins);
        ingredient.setCarbs(carbs);
        ingredient.setFat(fat);
        ingredient.setType(type);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ingredients").child(ingredient.getId()+"").setValue(ingredient);
        mDatabase.child("ingredientMaxId").setValue(ingredient.getId()+1L);
    }

    public void updateIngredient(String name, String type, Long proteins, Long carbs, Long fat){
        infos = infos.getInstance();
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setProteins(proteins);
        ingredient.setCarbs(carbs);
        ingredient.setFat(fat);
        ingredient.setType(type);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ingredients").child(ingredient.getId()+"").setValue(ingredient);
    }

    public Ingredient getIngredientById(Long ingredientId){
        Ingredient ingredient = new Ingredient();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ingredients").child(ingredientId+"").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    ingredient.setId(1L);
                    ingredient.setName("TODO CHANGE");
                    ingredient.setProteins(1L);
                    ingredient.setCarbs(2L);
                    ingredient.setFat(3L);
                    ingredient.setType("TODO CHANGE");
                }
            }
        });
        return ingredient;
    }

}