package com.example.myapplicationtest.factory;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplicationtest.entity.User;
import com.example.myapplicationtest.utils.Infos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserFactory {

    private DatabaseReference mDatabase;

    private Infos infos;

    public void writeNewUser(String name, String email) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(infos.getUser().username).setValue(infos.getUser());
    }

    public void updateUser(String userId, String name, String email){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(infos.getUser().username).child("username").setValue(name);
    }

    public void getUserById(String userId){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(infos.getUser().username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}
