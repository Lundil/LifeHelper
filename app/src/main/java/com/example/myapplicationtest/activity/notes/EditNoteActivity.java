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

public class EditNoteActivity extends AppCompatActivity {

    private EditText noteEditText;
    private int position;
    private String noteText = "";

    private String fileName;

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
        setContentView(R.layout.activity_edit_note);

        noteEditText = findViewById(R.id.noteEditText);

        String note = getIntent().getStringExtra("note");
        position = getIntent().getIntExtra("position", -1);
        noteText = getIntent().getStringExtra("noteText");
        fileName = getIntent().getStringExtra("fileName");

        noteEditText.setText(noteText);

        Button homeButton = findViewById(R.id.buttonHome);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                Intent intent = new Intent(EditNoteActivity.this, NotesActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        Button deleteButton = findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                deleteNoteButtonClicked(v);
                Intent intent = new Intent(EditNoteActivity.this, NotesActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        Button saveButton = findViewById(R.id.saveNoteButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                saveNoteButtonClicked(v);
                Intent intent = new Intent(EditNoteActivity.this, NotesActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

    }

    public void saveNoteButtonClicked(View view) {
        String note = noteEditText.getText().toString();
        new NotesFactory(view.getContext()).createOrUpdate(fileName, note);
        Intent intent = new Intent(EditNoteActivity.this, NotesActivity.class);
        intent.putExtra("note", note);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void deleteNoteButtonClicked(View view) {
        new NotesFactory(view.getContext()).delete(fileName);
        Intent intent = new Intent(EditNoteActivity.this, NotesActivity.class);
        setResult(RESULT_OK, intent);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
