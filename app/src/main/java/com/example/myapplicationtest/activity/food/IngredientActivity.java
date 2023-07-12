package com.example.myapplicationtest.activity.food;

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
import com.example.myapplicationtest.activity.MainActivity;
import com.example.myapplicationtest.adapter.IngredientAdapter;
import com.example.myapplicationtest.adapter.MovieAdapter;
import com.example.myapplicationtest.entity.ShoppingList;
import com.example.myapplicationtest.entity.User;
import com.example.myapplicationtest.entity.food.Ingredient;
import com.example.myapplicationtest.enums.TypeIngredient;
import com.example.myapplicationtest.factory.FoodFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class IngredientActivity extends AppCompatActivity {

    private List<Ingredient> listIngredient = new ArrayList<>();
    private List<Ingredient> listIngredientBackUp = new ArrayList<>();

    private ListView ingredientListView;
    private IngredientAdapter ingredientAdapter;

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
        setContentView(R.layout.activity_ingredients);


        // Obtenez une référence au bouton Accueil
        Button homeButton = findViewById(R.id.buttonReturn);
        Button addButton = findViewById(R.id.addButton);
        EditText search = findViewById(R.id.addEditView);

        // Ajoutez un écouteur de clic pour le bouton Accueil
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                Intent intent = new Intent(IngredientActivity.this, FoodActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        //TODO récupérer les ingrédients depuis Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ingredients");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //{"id":"1","name":"name","type":"TEST","proteins":"0","carbs":"0","fat":"0"}
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                listIngredientBackUp.clear();
                listIngredient.clear();
                while(iterator.hasNext()) {
                    listIngredientBackUp.add(iterator.next().getValue(Ingredient.class));
                }
                listIngredient.addAll(listIngredientBackUp);
                ingredientAdapter = new IngredientAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listIngredientBackUp);
                ingredientListView = findViewById(R.id.ingredientListView);
                ingredientListView.setAdapter(ingredientAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        ingredientAdapter = new IngredientAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listIngredient);
        ingredientListView = findViewById(R.id.ingredientListView);
        ingredientListView.setAdapter(ingredientAdapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                listIngredient.clear();
                if(s.length() == 0){
                    listIngredient.addAll(listIngredientBackUp);
                }else {
                    for (Ingredient ingredient : listIngredientBackUp) {
                        if(ingredient.getName().toLowerCase().contains(s.toString().toLowerCase())){
                            listIngredient.add(ingredient);
                        }
                    }
                }

                ingredientAdapter = new IngredientAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listIngredient);
                ingredientListView = findViewById(R.id.ingredientListView);
                ingredientListView.setAdapter(ingredientAdapter);
                ingredientAdapter.notifyDataSetChanged();

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Ingredient ing = (Ingredient) adapterView.getItemAtPosition(i);

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_add_ingredient, null);
                TextView textView = popupView.findViewById(R.id.titreTextView);
                Button saveButton = popupView.findViewById(R.id.saveButton);
                Button dismissButton = popupView.findViewById(R.id.dismissButton);
                EditText nameEditText = popupView.findViewById(R.id.nameEditText);
                EditText proteinEditText = popupView.findViewById(R.id.proteinEditText);
                EditText carbEditText = popupView.findViewById(R.id.carbEditText);
                EditText fatEditText = popupView.findViewById(R.id.fatEditText);
                Spinner mySpinner = (Spinner) popupView.findViewById(R.id.typeSpinner);
                mySpinner.setAdapter(new ArrayAdapter<TypeIngredient>(getApplicationContext(), android.R.layout.simple_spinner_item, TypeIngredient.values()));

                //set
                textView.setText("Change Ingredient");
                nameEditText.setText(ing.getName());
                proteinEditText.setText(ing.getProteins().toString());
                carbEditText.setText(ing.getCarbs().toString());
                fatEditText.setText(ing.getFat().toString());
                mySpinner.setSelection(getIndex(mySpinner, ing.getType()));

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
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
                        Long protein = Long.parseLong(proteinEditText.getText().toString());
                        Long carbs = Long.parseLong(carbEditText.getText().toString());
                        Long fat = Long.parseLong(fatEditText.getText().toString());

                        new FoodFactory(getApplicationContext()).updateIngredient(ing.getId().toString(),name, type, protein, carbs, fat);
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
                View popupView = inflater.inflate(R.layout.popup_add_ingredient, null);
                Button saveButton = popupView.findViewById(R.id.saveButton);
                EditText nameEditText = popupView.findViewById(R.id.nameEditText);
                EditText proteinEditText = popupView.findViewById(R.id.proteinEditText);
                EditText carbEditText = popupView.findViewById(R.id.carbEditText);
                EditText fatEditText = popupView.findViewById(R.id.fatEditText);
                Spinner mySpinner = (Spinner) popupView.findViewById(R.id.typeSpinner);
                mySpinner.setAdapter(new ArrayAdapter<TypeIngredient>(getApplicationContext(), android.R.layout.simple_spinner_item, TypeIngredient.values()));
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
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = String.valueOf(nameEditText.getText());
                        String type = mySpinner.getSelectedItem().toString();
                        Long protein = Long.parseLong(proteinEditText.getText().toString());
                        Long carbs = Long.parseLong(carbEditText.getText().toString());
                        Long fat = Long.parseLong(fatEditText.getText().toString());

                        new FoodFactory(getApplicationContext()).writeNewIngredient(name, type, protein, carbs, fat);
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
