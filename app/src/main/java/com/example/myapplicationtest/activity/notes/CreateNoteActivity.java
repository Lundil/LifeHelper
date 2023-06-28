package com.example.myapplicationtest.activity.notes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.factory.NotesFactory;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText noteEditText;
    private Button returnButton;

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
        setContentView(R.layout.activity_create_note);

        noteEditText = findViewById(R.id.noteEditText);
        returnButton = findViewById(R.id.buttonHome);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                Intent intent = new Intent(CreateNoteActivity.this, NotesActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

    }

    public void saveNoteButtonClicked(View view) {
        NotesFactory notes = new NotesFactory(view.getContext());
        int nextNote = notes.getAllNotes().size()+1;
        notes.createOrUpdate("note"+nextNote+".txt", noteEditText.getText().toString());
        Intent intent = new Intent(view.getContext(), NotesActivity.class);
        view.getContext().startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
