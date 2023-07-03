package com.example.myapplicationtest.factory;

import com.example.myapplicationtest.entity.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserFactory {

    private DatabaseReference mDatabase;

    public void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).setValue(user);
    }

    public void updateUser(String userId, String name, String email){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).child("username").setValue(name);
    }
}
