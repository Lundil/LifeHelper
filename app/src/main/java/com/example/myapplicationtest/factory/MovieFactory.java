package com.example.myapplicationtest.factory;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.myapplicationtest.activity.movies.WatchlistActivity;
import com.example.myapplicationtest.entity.Movie;
import com.example.myapplicationtest.utils.JSONReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieFactory {
    private static final String MOVIES_DIRECTORY = "movies";
    private Context context;

    private ObjectMapper objectMapper = new ObjectMapper();

    public MovieFactory(Context context) {
        this.context = context;
    }


    public void checkInfos(String nomFilm, View v){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    //Your code goes here
                    URL obj = new URL("https://streaming-availability.p.rapidapi.com/v2/search/title?title="+nomFilm+"&country=fr&show_type=movie&output_language=en");
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-RapidAPI-Key","e9ef8f8dcamsh879ac50ac13e3c3p1fd3c4jsnfed0bc63ae75");
                    con.setRequestProperty("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com");
                    con.setRequestProperty("Content-Type", "application/json");

                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) { // success
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        // print result
                        System.out.println(response.toString());
                        String responseJson = response.toString();

                        //Movie movie = objectMapper.readValue(json, Movie.class);
                        JsonNode test = objectMapper.readTree(responseJson);
                        if(objectMapper.readTree(responseJson) != null &&
                                objectMapper.readTree(responseJson).get("result") != null &&
                                objectMapper.readTree(responseJson).get("result").get(0) != null){
                            JsonNode node = objectMapper.readTree(responseJson).get("result").get(0);
                            JSONReader jsonReader = new JSONReader();

                            //Récupérer infos
                            String titre = (node.get("title") != null) ? node.get("title").asText() : "";
                            String year = (node.get("year") != null) ? node.get("year").asText() : "";
                            String backdropPath = (node.get("backdropPath") != null) ? node.get("backdropPath").asText() : "";
                            String posterPath = (node.get("posterPath") != null) ? node.get("posterPath").asText() : "";
                            String tagline = (node.get("tagline") != null) ? node.get("tagline").asText() : "";
                            Boolean apple = null, disney = null, prime = null;
                            if(node.get("streamingInfo") != null && node.get("streamingInfo").get("fr") != null){
                                apple = (node.get("streamingInfo").get("fr").get("apple") != null) ? node.get("streamingInfo").get("fr").get("apple").has(0) : null;
                                disney = (node.get("streamingInfo").get("fr").get("disney") != null) ? node.get("streamingInfo").get("fr").get("disney").has(0) : null;
                                prime = (node.get("streamingInfo").get("fr").get("prime") != null) ? node.get("streamingInfo").get("fr").get("prime").has(0) : null;
                            }

                            //Créer un film
                            Movie movie = new Movie(context);
                            movie.setTitre(titre);
                            movie.setYear(year);
                            movie.setBackdropPath("https://image.tmdb.org/t/p/w300"+backdropPath);
                            movie.setPosterPath("https://image.tmdb.org/t/p/w300"+posterPath);
                            movie.setTagline(tagline);
                            movie.setApple((null == apple) ? null : apple.booleanValue());
                            movie.setDisney((null == disney) ? null : disney.booleanValue());
                            movie.setPrime((null == prime) ? null : prime.booleanValue());
                            createMovie(movie);
                            Intent intent = new Intent(v.getContext(), WatchlistActivity.class);
                            v.getContext().startActivity(intent);
                            //new RaccoonActivity.DownloadImageTask(imageView).execute(message);
                        }else{
                            Toast.makeText(v.getContext(), "Pas de résultat :(",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(v.getContext(), "Pas de résultat :(",Toast.LENGTH_SHORT).show();
                    }
                } catch(IOException ioe){
                    Toast.makeText(v.getContext(), "Pas de résultat :(",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void createMovie(Movie movie) {
        try {
            File moviesDirectory = getMoviesDirectory();
            if (!moviesDirectory.exists()) {
                moviesDirectory.mkdirs();
            }
            objectMapper.writeValue(new File(moviesDirectory,"movie"+ movie.getId()+".json"), movie);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        try {
            File moviesDirectory = getMoviesDirectory();
            if (moviesDirectory.exists() && moviesDirectory.isDirectory()) {
                File[] movieFiles = moviesDirectory.listFiles();
                if (movieFiles != null) {
                    for (File movieFile : movieFiles) {
                        movieList.add(objectMapper.readValue(movieFile, Movie.class));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public void deleteAllMovies() {
        List<String> movieList = new ArrayList<>();
        File moviesDirectory = getMoviesDirectory();
        if (moviesDirectory.exists() && moviesDirectory.isDirectory()) {
            File[] movieFiles = moviesDirectory.listFiles();
            if (movieFiles != null) {
                for (File movieFile : movieFiles) {
                    if (movieFile.exists()) {
                        movieFile.delete();
                    }
                }
            }
        }
    }

    public void removeById(int id){
        File moviesDirectory = getMoviesDirectory();
        if (moviesDirectory.exists() && moviesDirectory.isDirectory()) {
            File[] movieFiles = moviesDirectory.listFiles();
            if (movieFiles != null) {
                for (File movieFile : movieFiles) {
                    if (movieFile.exists() && movieFile.getName()
                            .replaceAll("movie","")
                            .replaceAll(".json","").equalsIgnoreCase(id + "")) {
                        movieFile.delete();
                    }
                }
            }
        }
    }

    public void delete(String filename) {
        File moviesDirectory = getMoviesDirectory();
        if (moviesDirectory.exists() && moviesDirectory.isDirectory()) {
            File movieFile = new File(moviesDirectory, filename);
            if (movieFile.exists()) {
                movieFile.delete();
            }
        }
    }

    private File getMoviesDirectory() {
        return new File(context.getFilesDir(), MOVIES_DIRECTORY);
    }

    public int getLastIdMovie(){
        int id = 0;
        int currentId = 0;
        File moviesDirectory = getMoviesDirectory();
        if (moviesDirectory.exists() && moviesDirectory.isDirectory()) {
            File[] movieFiles = moviesDirectory.listFiles();
            if (movieFiles != null) {
                for (File movieFile : movieFiles) {
                    if (movieFile.exists()){
                        currentId = Integer.parseInt(movieFile.getName().replaceAll("movie","").replaceAll(".json",""));
                        if(currentId >= id){
                            id = currentId + 1;
                        }
                    }
                }
            }
        }
        return id;
    }

}