package com.example.myapplicationtest.activity.gym;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.adapter.ExerciceAdapter;
import com.example.myapplicationtest.adapter.WorkoutAdapter;
import com.example.myapplicationtest.adapter.WorkoutPartAdapter;
import com.example.myapplicationtest.entity.food.WorkoutPart;
import com.example.myapplicationtest.entity.gym.Exercice;
import com.example.myapplicationtest.entity.gym.Workout;
import com.example.myapplicationtest.enums.TypeWorkout;
import com.example.myapplicationtest.factory.GymFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {
    private List<Workout> listWorkouts = new ArrayList<>();
    private List<Workout> listWorkoutsBackUp = new ArrayList<>();
    private ListView workoutListView;
    private WorkoutAdapter workoutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Masquer la barre de navigation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        setContentView(R.layout.activity_workout);

        Button homeButton = findViewById(R.id.buttonReturn);
        Button addButton = findViewById(R.id.addButton);
        EditText search = findViewById(R.id.addEditView);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, GymActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("workouts");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //{"id":"1","name":"name","type":"TEST","proteins":"0","carbs":"0","fat":"0"}
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                listWorkoutsBackUp.clear();
                listWorkouts.clear();
                while(iterator.hasNext()) {
                    listWorkoutsBackUp.add(iterator.next().getValue(Workout.class));
                }
                listWorkouts.addAll(listWorkoutsBackUp);
                workoutAdapter = new WorkoutAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listWorkoutsBackUp);
                workoutListView = findViewById(R.id.workoutListView);
                workoutListView.setAdapter(workoutAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        workoutAdapter = new WorkoutAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listWorkoutsBackUp);
        workoutListView = findViewById(R.id.workoutListView);
        workoutListView.setAdapter(workoutAdapter);
        //champs de recherche de workout
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                listWorkouts.clear();
                if(s.length() == 0){
                    listWorkouts.addAll(listWorkoutsBackUp);
                }else {
                    for (Workout workout : listWorkoutsBackUp) {
                        if(workout.getName().toLowerCase().contains(s.toString().toLowerCase())){
                            listWorkouts.add(workout);
                        }
                    }
                }

                workoutAdapter = new WorkoutAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listWorkouts);
                workoutListView = findViewById(R.id.workoutListView);
                workoutListView.setAdapter(workoutAdapter);
                workoutAdapter.notifyDataSetChanged();

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        //on click d'un workout existant
        //TODO go vers une activité dédiée à la saisie d'un workout
        workoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Workout workout = (Workout) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(WorkoutActivity.this, WorkoutAddSaveActivity.class);
                intent.putExtra("workout",workout);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        //Add a workout
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, WorkoutAddSaveActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString.replaceAll("'",""))){
                return i;
            }
        }

        return 0;
    }
}
