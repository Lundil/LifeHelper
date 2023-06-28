package com.example.myapplicationtest.activity.movies;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.activity.MainActivity;
import com.example.myapplicationtest.adapter.MovieAdapter;
import com.example.myapplicationtest.entity.Movie;
import com.example.myapplicationtest.factory.MovieFactory;

import java.util.List;

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

        // Ajoutez un écouteur de clic pour le bouton Accueil
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                /*Intent intent = new Intent(WatchlistActivity.this, SearchMovieActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();*/
                new MovieFactory(getApplicationContext()).checkInfos( "Hidden Figures");
            }
        });

        MovieFactory movies = new MovieFactory(getApplicationContext());
        //notes.deleteAllNotes();
        movieList = movies.getAllMovies();
        movieAdapter = new MovieAdapter(this, android.R.layout.simple_list_item_1, movieList);
        movieListView = findViewById(R.id.movieListView);
        movieListView.setAdapter(movieAdapter);


        /*
        * https://dog.ceo/api/breeds/image/random
        * */
                /*
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("))
		.header("X-RapidAPI-Key", "e9ef8f8dcamsh879ac50ac13e3c3p1fd3c4jsnfed0bc63ae75")
		.header("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());
        * */

    }
}
