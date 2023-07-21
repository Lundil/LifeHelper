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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class WorkoutAddExerciceActivity extends AppCompatActivity {
    private List<Exercice> listExercice = new ArrayList<>();
    private List<Exercice> listExerciceBackUp = new ArrayList<>();
    private ListView exerciceListView;
    private Integer workoutPartPosition = 0;
    private Button homeButton;
    private Button saveButton;
    private EditText serieEditText;
    private EditText repEditText;
    private EditText poidsEditText;
    private EditText searchExerciceEditView;
    private WorkoutPart workoutPart = null;
    private Workout workout = new Workout();
    private ExerciceAdapter exerciceAdapter;
    private boolean save = false;

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
        setContentView(R.layout.popup_add_workout_part);

        homeButton = findViewById(R.id.buttonReturn);
        saveButton = findViewById(R.id.saveButton);
        serieEditText = findViewById(R.id.serieEditText);
        repEditText = findViewById(R.id.repEditText);
        poidsEditText = findViewById(R.id.poidsEditText);
        searchExerciceEditView = findViewById(R.id.searchExerciceEditView);
        exerciceListView = findViewById(R.id.exerciceListView);
        exerciceAdapter = new ExerciceAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listExercice);
        exerciceListView.setAdapter(exerciceAdapter);
        workout = (Workout) getIntent().getSerializableExtra("workout");

        //Set
        if(getIntent().getSerializableExtra("workoutPart") != null) {
            save = true;
            workoutPart = (WorkoutPart) getIntent().getSerializableExtra("workoutPart");
            if(workoutPart != null){
                workoutPartPosition = getIntent().getIntExtra("workoutPartPosition",-1);
                serieEditText.setText(workoutPart.getSerie().toString());
                repEditText.setText(workoutPart.getRep().toString());
                poidsEditText.setText(workoutPart.getPoids().toString());
                int position = getIndexListView(exerciceListView,workoutPart.getExo());
                exerciceListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                /*exerciceListView.performItemClick(
                        exerciceListView.getAdapter().getView(1,null,null),
                        1,
                        exerciceListView.getAdapter().getItemId(1)
                );*/
                searchExerciceEditView.setText(workoutPart.getExo().getName());
                //TODO select the right item in listview
            }

        }

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutAddExerciceActivity.this, WorkoutAddSaveActivity.class);
                if(getIntent().getSerializableExtra("workout") != null){
                    Workout workout = (Workout)getIntent().getSerializableExtra("workout");
                    intent.putExtra("workout",workout);
                }
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO save workout
                if(serieEditText.getText().toString().isEmpty() ||
                    repEditText.getText().toString().isEmpty() ||
                    poidsEditText.getText().toString().isEmpty() ||
                        exerciceListView.getCheckedItemPosition() == -1){
                    Toast.makeText(getApplicationContext(), "Il faut remplir les champs", Toast.LENGTH_SHORT).show();
                }else{
                    if(workoutPart != null){
                        //Update Data
                        workout.getListWorkoutPart().get(workoutPartPosition).setSerie(Long.parseLong(serieEditText.getText().toString()));
                        workout.getListWorkoutPart().get(workoutPartPosition).setRep(Long.parseLong(repEditText.getText().toString()));
                        workout.getListWorkoutPart().get(workoutPartPosition).setPoids(Long.parseLong(poidsEditText.getText().toString()));
                        int pos = exerciceListView.getCheckedItemPosition();
                        workout.getListWorkoutPart().get(workoutPartPosition).setExo((Exercice) exerciceListView.getAdapter().getItem(pos));

                    }else{
                        //Save
                        workoutPart = new WorkoutPart();
                        workoutPart.setSerie(Long.parseLong(serieEditText.getText().toString()));
                        workoutPart.setRep(Long.parseLong(repEditText.getText().toString()));
                        workoutPart.setPoids(Long.parseLong(poidsEditText.getText().toString()));
                        int pos = exerciceListView.getCheckedItemPosition();
                        workoutPart.setExo((Exercice) exerciceListView.getAdapter().getItem(pos));
                        List<WorkoutPart> listWP = workout.getListWorkoutPart();
                        listWP.add(workoutPart);
                        workout.setListWorkoutPart(listWP);
                    }




                    Intent intent = new Intent(WorkoutAddExerciceActivity.this, WorkoutAddSaveActivity.class);
                    intent.putExtra("workout",workout);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
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
                exerciceListView.setAdapter(exerciceAdapter);
                exerciceAdapter.notifyDataSetChanged();

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        //Load exercices
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myExoRef = database.getReference("exercices");
        myExoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                listExerciceBackUp.clear();
                listExercice.clear();
                while(iterator.hasNext()) {
                    listExerciceBackUp.add(iterator.next().getValue(Exercice.class));
                }
                listExercice.addAll(listExerciceBackUp);
                exerciceAdapter = new ExerciceAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listExerciceBackUp);
                ((ListView) findViewById(R.id.exerciceListView)).setAdapter(exerciceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
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

    private int getIndexListView(ListView listView, Exercice exercice){
        for (int i=0;i<listView.getCount();i++){
            if (listView.getItemAtPosition(i) == exercice){
                return i;
            }
        }

        return 0;
    }
}
