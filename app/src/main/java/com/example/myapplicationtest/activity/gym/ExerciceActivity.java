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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.adapter.ExerciceAdapter;
import com.example.myapplicationtest.entity.gym.Exercice;
import com.example.myapplicationtest.enums.TypeExercice;
import com.example.myapplicationtest.factory.GymFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExerciceActivity extends AppCompatActivity {

    private List<Exercice> listExercice = new ArrayList<>();
    private List<Exercice> listExerciceBackUp = new ArrayList<>();

    private ListView exerciceListView;
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
        setContentView(R.layout.activity_exercice);


        // Obtenez une référence au bouton Accueil
        Button homeButton = findViewById(R.id.buttonReturn);
        Button addButton = findViewById(R.id.addButton);
        EditText search = findViewById(R.id.addEditView);

        // Ajoutez un écouteur de clic pour le bouton Accueil
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                Intent intent = new Intent(ExerciceActivity.this, GymActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        //TODO récupérer les exercices depuis Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("exercices");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //{"id":"1","name":"nomExercice1","type":"Tout","image":"https://www.referenseo.com/wp-content/uploads/2019/03/image-attractive.jpg","tips":"Faire doucement"}
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                listExerciceBackUp.clear();
                listExercice.clear();
                while(iterator.hasNext()) {
                    listExerciceBackUp.add(iterator.next().getValue(Exercice.class));
                }
                listExercice.addAll(listExerciceBackUp);
                exerciceAdapter = new ExerciceAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listExerciceBackUp);
                exerciceListView = findViewById(R.id.exerciceListView);
                exerciceListView.setAdapter(exerciceAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        exerciceAdapter = new ExerciceAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listExercice);
        exerciceListView = findViewById(R.id.exerciceListView);
        exerciceListView.setAdapter(exerciceAdapter);

        search.addTextChangedListener(new TextWatcher() {
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
                exerciceListView = findViewById(R.id.exerciceListView);
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


        exerciceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Exercice exo = (Exercice) adapterView.getItemAtPosition(i);

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_add_exercice, null);
                TextView textView = popupView.findViewById(R.id.titreTextView);
                Button saveButton = popupView.findViewById(R.id.saveButton);
                Button dismissButton = popupView.findViewById(R.id.dismissButton);

                EditText nameEditText = popupView.findViewById(R.id.nameEditText);
                EditText imageEditText = popupView.findViewById(R.id.imageEditText);
                EditText tipsEditText = popupView.findViewById(R.id.tipsEditText);
                Spinner mySpinner = popupView.findViewById(R.id.typeSpinner);
                mySpinner.setAdapter(new ArrayAdapter<TypeExercice>(getApplicationContext(), android.R.layout.simple_spinner_item, TypeExercice.values()));

                //set
                textView.setText("Change Exercice");
                nameEditText.setText(exo.getName());
                imageEditText.setText(exo.getImage());
                tipsEditText.setText(exo.getTips());
                mySpinner.setSelection(getIndex(mySpinner, exo.getType()));

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = String.valueOf(nameEditText.getText());
                        String type = mySpinner.getSelectedItem().toString();
                        String image = imageEditText.getText().toString();
                        String tips = tipsEditText.getText().toString();

                        new GymFactory(getApplicationContext()).updateExercice(exo.getId().toString(), name, type, tips, image);
                        search.setText(search.getText());
                        popupWindow.dismiss();
                    }
                });
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_add_exercice, null);
                Button dismissButton = popupView.findViewById(R.id.dismissButton);
                Button saveButton = popupView.findViewById(R.id.saveButton);

                EditText nameEditText = popupView.findViewById(R.id.nameEditText);
                EditText imageEditText = popupView.findViewById(R.id.imageEditText);
                EditText tipsEditText = popupView.findViewById(R.id.tipsEditText);
                Spinner mySpinner = popupView.findViewById(R.id.typeSpinner);
                mySpinner.setAdapter(new ArrayAdapter<TypeExercice>(getApplicationContext(), android.R.layout.simple_spinner_item, TypeExercice.values()));

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = String.valueOf(nameEditText.getText());
                        String type = mySpinner.getSelectedItem().toString();
                        String image = imageEditText.getText().toString();
                        String tips = tipsEditText.getText().toString();

                        new GymFactory(getApplicationContext()).writeNewExercice(name, type, tips, image);
                        popupWindow.dismiss();
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
