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

public class WorkoutAddSaveActivity extends AppCompatActivity {
    private List<Workout> listWorkouts = new ArrayList<>();
    private List<Workout> listWorkoutsBackUp = new ArrayList<>();
    private List<Exercice> listExercice = new ArrayList<>();
    private List<Exercice> listExerciceBackUp = new ArrayList<>();
    private Workout workout = new Workout();
    private ListView workoutListView;
    private ListView workoutPartListView;

    private boolean save = false;
    private ListView exerciceListView;
    private WorkoutAdapter workoutAdapter;
    private WorkoutPartAdapter workoutPartAdapter;
    private ExerciceAdapter exerciceAdapter;
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
        setContentView(R.layout.popup_add_workout);

        TextView titreTextView = findViewById(R.id.titreTextView);
        Button homeButton = findViewById(R.id.buttonReturn);
        Button addExerciceButton = findViewById(R.id.addExerciceButton);
        Button saveButton = findViewById(R.id.saveButton);
        ListView workoutPartListView = findViewById(R.id.workoutPartListView);
        EditText nameEditText = findViewById(R.id.nameEditText);
        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        typeSpinner.setAdapter(new ArrayAdapter<TypeWorkout>(getApplicationContext(), android.R.layout.simple_spinner_item, TypeWorkout.values()));

        if((Workout) getIntent().getSerializableExtra("workout") != null) {
            //Maj IHM avec infos workout selectionné
            workout = (Workout) getIntent().getSerializableExtra("workout");
            if(workout.getId() != null){
                save = true;
                titreTextView.setText("Edit Workout");
            }
            nameEditText.setText(workout.getName());
            typeSpinner.setSelection(getIndex(typeSpinner, workout.getType()));
            if(workout.getListWorkoutPart() != null){
                workoutPartAdapter = new WorkoutPartAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, workout.getListWorkoutPart());
                workoutPartListView.setAdapter(workoutPartAdapter);
            }
        }else{
            workoutPartAdapter = new WorkoutPartAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
            workoutPartListView.setAdapter(workoutPartAdapter);

        }

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutAddSaveActivity.this, WorkoutActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEditText.getText().toString().isEmpty() ||
                   typeSpinner.getSelectedItem().toString().isEmpty() ||
                        workoutPartListView.getAdapter() == null||
                        workoutPartListView.getAdapter().getCount() == 0){
                   Toast.makeText(getApplicationContext(), "Saisir les infos du workout", Toast.LENGTH_LONG).show();
                }else{
                    //TODO save workout
                    workout.setName(nameEditText.getText().toString());
                    workout.setType(typeSpinner.getSelectedItem().toString());
                    List<WorkoutPart> listTmp = new ArrayList<>();
                    for (int i=0;i < workoutPartListView.getAdapter().getCount();i++){
                        listTmp.add((WorkoutPart) workoutPartListView.getAdapter().getItem(i));
                    }
                    workout.setListWorkoutPart(listTmp);
                    if(save) {
                        new GymFactory(getApplicationContext()).updateWorkout(workout);
                        Toast.makeText(getApplicationContext(), "Workout updated", Toast.LENGTH_LONG).show();
                    }else{
                        new GymFactory(getApplicationContext()).writeNewWorkout(workout);
                        Toast.makeText(getApplicationContext(), "Workout created", Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent(WorkoutAddSaveActivity.this, WorkoutActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        //Add a workout
        addExerciceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutAddSaveActivity.this, WorkoutAddExerciceActivity.class);
                workout.setName(nameEditText.getText().toString());
                workout.setType(typeSpinner.getSelectedItem().toString());
                intent.putExtra("workout",workout);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        workoutPartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WorkoutPart workoutPart = (WorkoutPart) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(WorkoutAddSaveActivity.this, WorkoutAddExerciceActivity.class);
                workout.setName(nameEditText.getText().toString());
                workout.setType(typeSpinner.getSelectedItem().toString());
                intent.putExtra("workout",workout);
                intent.putExtra("workoutPart", workoutPart);
                intent.putExtra("workoutPartPosition", i);
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
