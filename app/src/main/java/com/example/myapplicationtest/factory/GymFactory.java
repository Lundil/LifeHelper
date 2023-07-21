package com.example.myapplicationtest.factory;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplicationtest.entity.food.Ingredient;
import com.example.myapplicationtest.entity.food.WorkoutPart;
import com.example.myapplicationtest.entity.gym.Exercice;
import com.example.myapplicationtest.entity.gym.Workout;
import com.example.myapplicationtest.utils.Infos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GymFactory {

    private DatabaseReference mDatabase;
    private Context context;
    private Infos infos;

    public GymFactory(Context context) {
        this.context = context;
    }

    public void writeNewExercice(String name, String type, String tips, String image) {
        infos = infos.getInstance();
        Exercice exercice = new Exercice();
        exercice.setId(infos.getMaxIdExercice());
        exercice.setName(name);
        exercice.setType(type);
        exercice.setTips(tips);
        exercice.setImage(image);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("exercices").child(exercice.getId()+"").setValue(exercice);
        mDatabase.child("exerciceMaxId").setValue(exercice.getId()+1L);
    }

    public void updateExercice(String id, String name, String type, String tips, String image){
        infos = infos.getInstance();
        Exercice exercice = new Exercice();
        exercice.setId(Long.parseLong(id));
        exercice.setName(name);
        exercice.setType(type);
        exercice.setTips(tips);
        exercice.setImage(image);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("exercices").child(exercice.getId()+"").setValue(exercice);
    }

    public void writeNewWorkout(Workout workout) {
        infos = infos.getInstance();
        workout.setId(infos.getMaxIdWorkout());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("workouts").child(workout.getId()+"").setValue(workout);
        mDatabase.child("workoutMaxId").setValue(workout.getId()+1L);

        for (WorkoutPart workoutPart : workout.getListWorkoutPart() ) {
            writeNewWorkoutPart(workoutPart);
        }
    }

    public void writeNewWorkoutPart(WorkoutPart workoutPart) {
        infos = infos.getInstance();
        workoutPart.setId(infos.getMaxIdWorkoutPart());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("workoutParts").child(workoutPart.getId()+"").setValue(workoutPart);
        mDatabase.child("workoutPartMaxId").setValue(workoutPart.getId()+1L);
    }

    public void updateWorkout(Workout workout){
        infos = infos.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("workouts").child(workout.getId()+"").setValue(workout);

        for (WorkoutPart workoutPart : workout.getListWorkoutPart() ) {
            updateWorkoutPart(workoutPart);
        }
    }

    public void updateWorkoutPart(WorkoutPart workoutPart){
        infos = infos.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("workoutParts").child(workoutPart.getId()+"").setValue(workoutPart);
    }

}