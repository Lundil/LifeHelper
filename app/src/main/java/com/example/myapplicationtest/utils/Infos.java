package com.example.myapplicationtest.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplicationtest.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Infos {

    private static Infos instance;
    private static User user = new User();
    private static Long maxIdIngredient = 1L;
    private static Long maxIdMeal = 1L;
    private static Long maxIdShoppingList = 1L;
    private static Long maxIdUberEat = 1L;
    private static Long maxIdMovie = 1L;
    private static Long maxIdNote = 1L;
    private static Long maxIdUser = 1L;

    private static Long maxIdExercice = 1L;
    private static Long maxIdWorkout = 1L;
    private static Long maxIdWorkoutPart = 1L;

    public static void initInstance() {
        if (instance == null) {
            // Create the instance
            instance = new Infos();
        }
    }

    private Infos() { }

    public void loadMaxId(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ingredientMaxId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.getValue(Long.class) != null){
                    maxIdIngredient = dataSnapshot.getValue(Long.class);
                }else{
                    maxIdIngredient = 1L;
                    mDatabase.child("ingredientMaxId").setValue(1L);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        mDatabase.child("mealMaxId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.getValue(Long.class) != null){
                    maxIdMeal = dataSnapshot.getValue(Long.class);
                }else{
                    maxIdMeal = 1L;
                    mDatabase.child("mealMaxId").setValue(1L);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        mDatabase.child("shoppingListMaxId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.getValue(Long.class) != null){
                    maxIdShoppingList = dataSnapshot.getValue(Long.class);
                }else{
                    maxIdShoppingList = 1L;
                    mDatabase.child("shoppingListMaxId").setValue(1L);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        mDatabase.child("uberEatMaxId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.getValue(Long.class) != null){
                    maxIdUberEat = dataSnapshot.getValue(Long.class);
                }else{
                    maxIdUberEat = 1L;
                    mDatabase.child("uberEatMaxId").setValue(1L);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        mDatabase.child("movieMaxId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.getValue(Long.class) != null){
                    maxIdMovie = dataSnapshot.getValue(Long.class);
                }else{
                    maxIdMovie = 1L;
                    mDatabase.child("movieMaxId").setValue(1L);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        mDatabase.child("noteMaxId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.getValue(Long.class) != null){
                    maxIdNote = dataSnapshot.getValue(Long.class);
                }else{
                    maxIdNote = 1L;
                    mDatabase.child("noteMaxId").setValue(1L);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        mDatabase.child("usersMaxId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.getValue(Long.class) != null){
                    maxIdUser = dataSnapshot.getValue(Long.class);
                }else{
                    maxIdUser = 1L;
                    mDatabase.child("usersMaxId").setValue(1L);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        mDatabase.child("exerciceMaxId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.getValue(Long.class) != null){
                    maxIdExercice = dataSnapshot.getValue(Long.class);
                }else{
                    maxIdExercice = 1L;
                    mDatabase.child("exerciceMaxId").setValue(1L);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        mDatabase.child("workoutMaxId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.getValue(Long.class) != null){
                    maxIdWorkout = dataSnapshot.getValue(Long.class);
                }else{
                    maxIdWorkout = 1L;
                    mDatabase.child("workoutMaxId").setValue(1L);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        mDatabase.child("workoutPartMaxId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.getValue(Long.class) != null){
                    maxIdWorkoutPart = dataSnapshot.getValue(Long.class);
                }else{
                    maxIdWorkoutPart = 1L;
                    mDatabase.child("workoutPartMaxId").setValue(1L);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    public static Infos getInstance() {
        return instance;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Infos.user = user;
    }

    public static Long getMaxIdIngredient() {
        return maxIdIngredient;
    }

    public static void setMaxIdIngredient(Long maxIdIngredient) {
        Infos.maxIdIngredient = maxIdIngredient;
    }

    public static Long getMaxIdMeal() {
        return maxIdMeal;
    }

    public static void setMaxIdMeal(Long maxIdMeal) {
        Infos.maxIdMeal = maxIdMeal;
    }

    public static Long getMaxIdShoppingList() {
        return maxIdShoppingList;
    }

    public static void setMaxIdShoppingList(Long maxIdShoppingList) {
        Infos.maxIdShoppingList = maxIdShoppingList;
    }

    public static Long getMaxIdUberEat() {
        return maxIdUberEat;
    }

    public static void setMaxIdUberEat(Long maxIdUberEat) {
        Infos.maxIdUberEat = maxIdUberEat;
    }

    public static Long getMaxIdMovie() {
        return maxIdMovie;
    }

    public static void setMaxIdMovie(Long maxIdMovie) {
        Infos.maxIdMovie = maxIdMovie;
    }

    public static Long getMaxIdNote() {
        return maxIdNote;
    }

    public static void setMaxIdNote(Long maxIdNote) {
        Infos.maxIdNote = maxIdNote;
    }

    public static Long getMaxIdUser() {
        return maxIdUser;
    }

    public static void setMaxIdUser(Long maxIdUser) {
        Infos.maxIdUser = maxIdUser;
    }

    public static Long getMaxIdExercice() {
        return maxIdExercice;
    }

    public static void setMaxIdExercice(Long maxIdExercice) {
        Infos.maxIdExercice = maxIdExercice;
    }

    public static Long getMaxIdWorkout() {
        return maxIdWorkout;
    }
    public static void setMaxIdWorkout(Long maxIdWorkout) {
        Infos.maxIdWorkout = maxIdWorkout;
    }

    public static Long getMaxIdWorkoutPart() {
        return maxIdWorkoutPart;
    }

    public static void setMaxIdWorkoutPart(Long maxIdWorkoutPart) {
        Infos.maxIdWorkoutPart = maxIdWorkoutPart;
    }
}
