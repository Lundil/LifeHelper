package com.example.myapplicationtest.activity.gym;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {
    private List<Workout> listWorkouts = new ArrayList<>();
    private List<Workout> listWorkoutsBackUp = new ArrayList<>();
    private List<Exercice> listExercice = new ArrayList<>();
    private List<Exercice> listExerciceBackUp = new ArrayList<>();

    private ListView workoutListView;
    private ListView workoutPartListView;

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

        //TODO récupérer les ingrédients depuis Firebase
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

        //Load exercices
        DatabaseReference myExoRef = database.getReference("exercices");
        myExoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //{"id":"1","name":"name","type":"TEST","proteins":"0","carbs":"0","fat":"0"}
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                listExerciceBackUp.clear();
                listExercice.clear();
                while(iterator.hasNext()) {
                    listExerciceBackUp.add(iterator.next().getValue(Exercice.class));
                }
                listExercice.addAll(listExerciceBackUp);
                exerciceAdapter = new ExerciceAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listExerciceBackUp);
                exerciceListView = findViewById(R.id.exerciceListView);
                if(exerciceListView != null){
                    exerciceListView.setAdapter(exerciceAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        //load workouts
        workoutAdapter = new WorkoutAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listWorkouts);
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
        workoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Workout workout = (Workout) adapterView.getItemAtPosition(i);

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_add_workout, null);
                TextView textView = popupView.findViewById(R.id.titreTextView);
                Button addExerciceButton = popupView.findViewById(R.id.addExerciceButton);
                Button saveButton = popupView.findViewById(R.id.saveButton);
                Button dismissButton = popupView.findViewById(R.id.dismissButton);

                //name, type, workoutpart
                EditText nameEditText = popupView.findViewById(R.id.nameEditText);
                Spinner mySpinner = (Spinner) popupView.findViewById(R.id.typeSpinner);
                mySpinner.setAdapter(new ArrayAdapter<TypeWorkout>(getApplicationContext(), android.R.layout.simple_spinner_item, TypeWorkout.values()));
                workoutListView = popupView.findViewById(R.id.workoutPartListView);
                //alimenter ces elements avec les infos du workout
                nameEditText.setText(workout.getName());
                mySpinner.setSelection(getIndex(mySpinner, workout.getType()));
                workoutPartAdapter = new WorkoutPartAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, workout.getListWorkoutPart());
                workoutListView = findViewById(R.id.workoutListView);
                workoutListView.setAdapter(workoutPartAdapter);

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                addExerciceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO Open popup ajout workoutpart
                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.popup_add_workout, null);
                        TextView textView = popupView.findViewById(R.id.titreTextView);
                        Button addExerciceButton = popupView.findViewById(R.id.addExerciceButton);
                        Button saveButton = popupView.findViewById(R.id.saveButton);
                        Button dismissButton = popupView.findViewById(R.id.dismissButton);

                        //name, type, workoutpart
                        EditText nameEditText = popupView.findViewById(R.id.nameEditText);
                        Spinner mySpinner = (Spinner) popupView.findViewById(R.id.typeSpinner);
                        mySpinner.setAdapter(new ArrayAdapter<TypeWorkout>(getApplicationContext(), android.R.layout.simple_spinner_item, TypeWorkout.values()));
                        workoutPartListView = popupView.findViewById(R.id.workoutPartListView);
                        // create the popup window
                        int width = LinearLayout.LayoutParams.MATCH_PARENT;
                        int height = LinearLayout.LayoutParams.MATCH_PARENT;
                        boolean focusable = true; // lets taps outside the popup also dismiss it
                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                        // show the popup window
                        // which view you pass in doesn't matter, it is only used for the window tolken
                        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                        dismissButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        saveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //TODO Save button clicked
                                Toast.makeText(getApplicationContext(), "Save button clicked", Toast.LENGTH_LONG).show();
                                //new FoodFactory(getApplicationContext()).writeNewIngredient(name, type, protein, carbs, fat);
                                //popupWindow.dismiss();
                            }
                        });
                        addExerciceButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO ouvrir popup sans infos du workout part
                                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                View popupView = inflater.inflate(R.layout.popup_add_workout_part, null);
                                //Fields
                                TextView textView = popupView.findViewById(R.id.addExercicesTextView);
                                EditText serieEditText = popupView.findViewById(R.id.serieEditText);
                                EditText repEditText = popupView.findViewById(R.id.repEditText);
                                EditText poidsEditText = popupView.findViewById(R.id.poidsEditText);
                                EditText searchExerciceEditView = popupView.findViewById(R.id.searchExerciceEditView);
                                ListView exerciceListView = popupView.findViewById(R.id.exerciceListView);
                                Button saveButton = popupView.findViewById(R.id.saveButton);
                                saveButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getApplicationContext(),"Save clicked", Toast.LENGTH_LONG).show();
                                        //TODO save exercice
                                        // new GymFactory(getApplicationContext()).writeNewWorkout(workout);
                                        //popupWindow.dismiss();
                                    }
                                });
                                searchExerciceEditView.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        listExercice.clear();
                                        if(s.length() == 0){
                                            listExercice.addAll(listExerciceBackUp);
                                        }else {
                                            for (Exercice exercice : listExerciceBackUp) {
                                                if(exercice.getName().toLowerCase().contains(s.toString().toLowerCase())){
                                                    listExercice.add(exercice);
                                                }
                                            }
                                        }

                                        exerciceAdapter = new ExerciceAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listExercice);
                                        ListView exerciceListView = findViewById(R.id.exerciceListView);
                                        exerciceListView.setAdapter(exerciceAdapter);
                                        exerciceAdapter.notifyDataSetChanged();

                                    }
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start,
                                                                  int count, int after) {
                                    }
                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }
                                });

                            }
                        });
                        workoutPartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //TODO ouvrir popup avec les infos du workout part
                                WorkoutPart workoutPart = (WorkoutPart) adapterView.getItemAtPosition(i);
                                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                View popupView = inflater.inflate(R.layout.popup_add_workout_part, null);
                                //Fields
                                TextView textView = popupView.findViewById(R.id.addExercicesTextView);
                                EditText serieEditText = popupView.findViewById(R.id.serieEditText);
                                EditText repEditText = popupView.findViewById(R.id.repEditText);
                                EditText poidsEditText = popupView.findViewById(R.id.poidsEditText);
                                EditText searchExerciceEditView = popupView.findViewById(R.id.searchExerciceEditView);
                                ListView exerciceListView = popupView.findViewById(R.id.exerciceListView);
                                Button saveButton = popupView.findViewById(R.id.saveButton);
                                //Set
                                textView.setText("Change Exercice");
                                serieEditText.setText(workoutPart.getSerie().toString());
                                repEditText.setText(workoutPart.getRep().toString());
                                poidsEditText.setText(workoutPart.getPoids().toString());

                                saveButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getApplicationContext(),"Save clicked", Toast.LENGTH_LONG).show();
                                        //TODO save exercice
                                        // new GymFactory(getApplicationContext()).writeNewWorkout(workout);
                                        //popupWindow.dismiss();
                                    }
                                });

                                searchExerciceEditView.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        listExercice.clear();
                                        if(s.length() == 0){
                                            listExercice.addAll(listExerciceBackUp);
                                        }else {
                                            for (Exercice exercice : listExerciceBackUp) {
                                                if(exercice.getName().toLowerCase().contains(s.toString().toLowerCase())){
                                                    listExercice.add(exercice);
                                                }
                                            }
                                        }

                                        exerciceAdapter = new ExerciceAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listExercice);
                                        ListView exerciceListView = findViewById(R.id.exerciceListView);
                                        exerciceListView.setAdapter(exerciceAdapter);
                                        exerciceAdapter.notifyDataSetChanged();

                                    }
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start,
                                                                  int count, int after) {
                                    }
                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }
                                });
                            }
                        });
                    }
                });
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        workout.setName(String.valueOf(nameEditText.getText()));
                        workout.setType(mySpinner.getSelectedItem().toString());
                        List<WorkoutPart> listTmp = new ArrayList<>();
                        for (int i=0;i<workoutAdapter.getCount();i++){
                            listTmp.add((WorkoutPart) workoutAdapter.getItem(i));
                        }
                        workout.setListWorkoutPart(listTmp);
                        new GymFactory(getApplicationContext()).writeNewWorkout(workout);
                        popupWindow.dismiss();
                    }
                });
            }
        });

        //Add a workout
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_add_workout, null);
                TextView textView = popupView.findViewById(R.id.titreTextView);
                Button addExerciceButton = popupView.findViewById(R.id.addExerciceButton);
                Button saveButton = popupView.findViewById(R.id.saveButton);
                Button dismissButton = popupView.findViewById(R.id.dismissButton);

                //name, type, workoutpart
                EditText nameEditText = popupView.findViewById(R.id.nameEditText);
                Spinner mySpinner = (Spinner) popupView.findViewById(R.id.typeSpinner);
                mySpinner.setAdapter(new ArrayAdapter<TypeWorkout>(getApplicationContext(), android.R.layout.simple_spinner_item, TypeWorkout.values()));
                workoutPartListView = popupView.findViewById(R.id.workoutPartListView);
                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TODO Save button clicked
                        Toast.makeText(getApplicationContext(), "Save button clicked", Toast.LENGTH_LONG).show();
                        //new FoodFactory(getApplicationContext()).writeNewIngredient(name, type, protein, carbs, fat);
                        //popupWindow.dismiss();
                    }
                });
                addExerciceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO ouvrir popup sans infos du workout part
                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.popup_add_workout_part, null);
                        //Fields
                        TextView textView = popupView.findViewById(R.id.addExercicesTextView);
                        EditText serieEditText = popupView.findViewById(R.id.serieEditText);
                        EditText repEditText = popupView.findViewById(R.id.repEditText);
                        EditText poidsEditText = popupView.findViewById(R.id.poidsEditText);
                        EditText searchExerciceEditView = popupView.findViewById(R.id.searchExerciceEditView);
                        ListView exerciceListView = popupView.findViewById(R.id.exerciceListView);
                        Button saveButton = popupView.findViewById(R.id.saveButton);
                        saveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),"Save clicked", Toast.LENGTH_LONG).show();
                                //TODO save exercice
                                // new GymFactory(getApplicationContext()).writeNewWorkout(workout);
                                //popupWindow.dismiss();
                            }
                        });
                        searchExerciceEditView.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void afterTextChanged(Editable s) {
                                listExercice.clear();
                                if(s.length() == 0){
                                    listExercice.addAll(listExerciceBackUp);
                                }else {
                                    for (Exercice exercice : listExerciceBackUp) {
                                        if(exercice.getName().toLowerCase().contains(s.toString().toLowerCase())){
                                            listExercice.add(exercice);
                                        }
                                    }
                                }

                                exerciceAdapter = new ExerciceAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listExercice);
                                ListView exerciceListView = findViewById(R.id.exerciceListView);
                                exerciceListView.setAdapter(exerciceAdapter);
                                exerciceAdapter.notifyDataSetChanged();

                            }
                            @Override
                            public void beforeTextChanged(CharSequence s, int start,
                                                          int count, int after) {
                            }
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }
                        });

                    }
                });
                workoutPartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //TODO ouvrir popup avec les infos du workout part
                        WorkoutPart workoutPart = (WorkoutPart) adapterView.getItemAtPosition(i);
                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.popup_add_workout_part, null);
                        //Fields
                        TextView textView = popupView.findViewById(R.id.addExercicesTextView);
                        EditText serieEditText = popupView.findViewById(R.id.serieEditText);
                        EditText repEditText = popupView.findViewById(R.id.repEditText);
                        EditText poidsEditText = popupView.findViewById(R.id.poidsEditText);
                        EditText searchExerciceEditView = popupView.findViewById(R.id.searchExerciceEditView);
                        ListView exerciceListView = popupView.findViewById(R.id.exerciceListView);
                        Button saveButton = popupView.findViewById(R.id.saveButton);
                        //Set
                        textView.setText("Change Exercice");
                        serieEditText.setText(workoutPart.getSerie().toString());
                        repEditText.setText(workoutPart.getRep().toString());
                        poidsEditText.setText(workoutPart.getPoids().toString());

                        saveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),"Save clicked", Toast.LENGTH_LONG).show();
                                //TODO save exercice
                                // new GymFactory(getApplicationContext()).writeNewWorkout(workout);
                                //popupWindow.dismiss();
                            }
                        });

                        searchExerciceEditView.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void afterTextChanged(Editable s) {
                                listExercice.clear();
                                if(s.length() == 0){
                                    listExercice.addAll(listExerciceBackUp);
                                }else {
                                    for (Exercice exercice : listExerciceBackUp) {
                                        if(exercice.getName().toLowerCase().contains(s.toString().toLowerCase())){
                                            listExercice.add(exercice);
                                        }
                                    }
                                }

                                exerciceAdapter = new ExerciceAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listExercice);
                                ListView exerciceListView = findViewById(R.id.exerciceListView);
                                exerciceListView.setAdapter(exerciceAdapter);
                                exerciceAdapter.notifyDataSetChanged();

                            }
                            @Override
                            public void beforeTextChanged(CharSequence s, int start,
                                                          int count, int after) {
                            }
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }
                        });
                    }
                });
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
