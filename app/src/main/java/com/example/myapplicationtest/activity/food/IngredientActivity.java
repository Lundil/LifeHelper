package com.example.myapplicationtest.activity.food;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.activity.MainActivity;
import com.example.myapplicationtest.adapter.IngredientAdapter;
import com.example.myapplicationtest.adapter.MovieAdapter;
import com.example.myapplicationtest.entity.ShoppingList;
import com.example.myapplicationtest.entity.User;
import com.example.myapplicationtest.entity.food.Ingredient;
import com.example.myapplicationtest.factory.FoodFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    private List<Ingredient> listIngredient = new ArrayList<>();

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
                listIngredient.clear();
                while(iterator.hasNext()) {
                    listIngredient.add(iterator.next().getValue(Ingredient.class));
                }
                ingredientAdapter = new IngredientAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listIngredient);
                ingredientListView = findViewById(R.id.ingredientListView);
                ingredientListView.setAdapter(ingredientAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_add_ingredient, null);
                Button saveButton = popupView.findViewById(R.id.saveButton);
                EditText nameEditText = popupView.findViewById(R.id.nameEditText);
                EditText typeEditText = popupView.findViewById(R.id.typeEditText);
                EditText proteinEditText = popupView.findViewById(R.id.proteinEditText);
                EditText carbEditText = popupView.findViewById(R.id.carbEditText);
                EditText fatEditText = popupView.findViewById(R.id.fatEditText);
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
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
                        //TODO ajouter ingredient et dismiss
                        String name = String.valueOf(nameEditText.getText());
                        String type = String.valueOf(typeEditText.getText());
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
}
