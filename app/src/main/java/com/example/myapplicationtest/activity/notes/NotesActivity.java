package com.example.myapplicationtest.activity.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.activity.MainActivity;
import com.example.myapplicationtest.adapter.NoteAdapter;
import com.example.myapplicationtest.entity.Note;
import com.example.myapplicationtest.factory.NotesFactory;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private static final int REQUEST_CREATE_NOTE = 1;
    private static final int REQUEST_EDIT_NOTE = 2;

    private List<Note> noteList;
    private ArrayAdapter<String> noteAdapter;
    private ListView noteListView;

    @SuppressLint("MissingInflatedId")
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
        setContentView(R.layout.activity_notes);

        // Obtenez une référence au bouton Accueil
        Button homeButton = findViewById(R.id.buttonReturn);
        Button createButton = findViewById(R.id.createNoteButton);

        // Ajoutez un écouteur de clic pour le bouton Accueil
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        noteList = new ArrayList<>();
        NotesFactory notes = new NotesFactory(getApplicationContext());
        //notes.deleteAllNotes();
        noteList = notes.getAllNotes();
        noteAdapter = new NoteAdapter(this, android.R.layout.simple_list_item_1, noteList);
        noteListView = findViewById(R.id.noteListView);
        noteListView.setAdapter(noteAdapter);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NotesActivity.this, EditNoteActivity.class);
                Note note = notes.getAllNotes().get(position);
                intent.putExtra("position",position);
                intent.putExtra("noteText",note.getText());
                intent.putExtra("fileName",noteList.get(position).getFileName());
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                Intent intent = new Intent(NotesActivity.this, CreateNoteActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });



    }

    private void editNote(String note, int position) {

    }

    private void loadNotes() {
        // Code pour charger les notes existantes depuis la base de données ou autre source
        // et ajouter les notes à la liste noteList
        NotesFactory notes = new NotesFactory(getApplicationContext());
        noteList = notes.getAllNotes();
    }

    private void deleteNote(int position) {
        // Code pour supprimer la note de la base de données ou autre source
        // et supprimer la note de la liste noteList
        NotesFactory notes = new NotesFactory(getApplicationContext());
        notes.removeById(position);
        noteList.remove(position);

    }

    private void createNote(String note) {

    }
    private void updateNote(String note, int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CREATE_NOTE) {
                String note = data.getStringExtra("note");
                createNote(note);
            } else if (requestCode == REQUEST_EDIT_NOTE) {
                String note = data.getStringExtra("note");
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    updateNote(note, position);
                }
            }
        }
    }

}
