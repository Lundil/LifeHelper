package com.example.myapplicationtest.activity.movies;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.activity.MainActivity;
import com.example.myapplicationtest.adapter.MovieAdapter;
import com.example.myapplicationtest.entity.Movie;
import com.example.myapplicationtest.factory.MovieFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class WatchlistActivity extends AppCompatActivity{

    private List<Movie> movieList;

    private ArrayAdapter<String> movieAdapter;
    private ListView movieListView;

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
        setContentView(R.layout.activity_watchlist);

        // Obtenez une référence au bouton Accueil
        Button homeButton = findViewById(R.id.buttonReturn);

        // Ajoutez un écouteur de clic pour le bouton Accueil
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                Intent intent = new Intent(WatchlistActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        Button searchButton = findViewById(R.id.searchButton);
        EditText addEditView = findViewById(R.id.addEditView);
        // Ajoutez un écouteur de clic pour le bouton Accueil
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                if(addEditView.getText() != null && addEditView.getText().toString() != ""){
                    try {
                        Toast.makeText(getApplicationContext(),"Attend ça charge un peu là", Toast.LENGTH_SHORT).show();
                        new MovieFactory(getApplicationContext()).checkInfos(
                                URLEncoder.encode(addEditView.getText().toString(), StandardCharsets.UTF_8.toString()),v);
                        movieList = new MovieFactory(getApplicationContext()).getAllMovies();
                        movieAdapter = new MovieAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, movieList);
                        movieListView = findViewById(R.id.movieListView);
                        movieListView.setAdapter(movieAdapter);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Nan mais saisie un truc aussi fais un effort", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MovieFactory movies = new MovieFactory(getApplicationContext());
        //movies.deleteAllMovies();
        movieList = movies.getAllMovies();
        movieAdapter = new MovieAdapter(this, android.R.layout.simple_list_item_1, movieList);
        movieListView = findViewById(R.id.movieListView);
        movieListView.setAdapter(movieAdapter);

    }
    public void deleteNoteButtonClicked(View v ){
        Toast.makeText(getApplicationContext(),"Test", Toast.LENGTH_SHORT).show();
        MovieFactory movies = new MovieFactory(getApplicationContext());
        movieList = movies.getAllMovies();
        movieAdapter = new MovieAdapter(this, android.R.layout.simple_list_item_1, movieList);
        movieListView = findViewById(R.id.movieListView);
        movieListView.setAdapter(movieAdapter);
    }
}
