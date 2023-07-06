package com.example.myapplicationtest.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplicationtest.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Infos {
    private Context appContext;
    private User user = null;
    private static final Infos infos = new Infos();

    private Long maxIdIngredient;
    private Long maxIdMeal;
    private Long maxIdShoppingList;
    private Long maxIdUberEat;
    private Long maxIdMovie;
    private Long maxIdNote;
    private Long maxIdUser;

    public void init(Context context) {
        if(appContext == null) {
            this.appContext = context;
        }
    }
    private Context getContext() {
        return appContext;
    }
    public static Context get() {
        return getInstance().getContext();
    }
    public static synchronized Infos getInstance() {
        return infos;
    }
    private Infos() { }

    public void storeUser(User user) {
        if(this.user == null) {
            this.user = user;
        }
    }
    public User getUser() {
        return this.user;
    }

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

    }

    public Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getMaxIdIngredient() {
        return maxIdIngredient;
    }

    public void setMaxIdIngredient(Long maxIdIngredient) {
        this.maxIdIngredient = maxIdIngredient;
    }

    public Long getMaxIdMeal() {
        return maxIdMeal;
    }

    public void setMaxIdMeal(Long maxIdMeal) {
        this.maxIdMeal = maxIdMeal;
    }

    public Long getMaxIdShoppingList() {
        return maxIdShoppingList;
    }

    public void setMaxIdShoppingList(Long maxIdShoppingList) {
        this.maxIdShoppingList = maxIdShoppingList;
    }

    public Long getMaxIdUberEat() {
        return maxIdUberEat;
    }

    public void setMaxIdUberEat(Long maxIdUberEat) {
        this.maxIdUberEat = maxIdUberEat;
    }

    public Long getMaxIdMovie() {
        return maxIdMovie;
    }

    public void setMaxIdMovie(Long maxIdMovie) {
        this.maxIdMovie = maxIdMovie;
    }

    public Long getMaxIdNote() {
        return maxIdNote;
    }

    public void setMaxIdNote(Long maxIdNote) {
        this.maxIdNote = maxIdNote;
    }

    public Long getMaxIdUser() {
        return maxIdUser;
    }

    public void setMaxIdUser(Long maxIdUser) {
        this.maxIdUser = maxIdUser;
    }
}
