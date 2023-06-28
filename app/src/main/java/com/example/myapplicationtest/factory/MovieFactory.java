package com.example.myapplicationtest.factory;


import android.content.Context;

import com.example.myapplicationtest.entity.Movie;
import com.example.myapplicationtest.entity.Note;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MovieFactory {
    private static final String NOTES_DIRECTORY = "movies";
    private Context context;

    public MovieFactory(Context context) {
        this.context = context;
    }


    public void checkInfos(String nomFilm){
        /*HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://streaming-availability.p.rapidapi.com/v2/search/title?title=hidden%20figures&country=fr&show_type=movie&output_language=en"))
                .header("X-RapidAPI-Key", "e9ef8f8dcamsh879ac50ac13e3c3p1fd3c4jsnfed0bc63ae75")
                .header("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());*/
    }

    public void createOrUpdate(String filename, String content) {
        try {
            File notesDirectory = getNotesDirectory();
            if (!notesDirectory.exists()) {
                notesDirectory.mkdirs();
            }

            File noteFile = new File(notesDirectory, filename);
            FileOutputStream outputStream = new FileOutputStream(noteFile);
            System.out.println(notesDirectory.getAbsolutePath());
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        try {
            File moviesDirectory = getNotesDirectory();
            if (moviesDirectory.exists() && moviesDirectory.isDirectory()) {
                File[] movieFiles = moviesDirectory.listFiles();
                if (movieFiles != null) {
                    for (File movieFile : movieFiles) {
                        StringBuilder content = new StringBuilder();
                        BufferedReader reader = new BufferedReader(new FileReader(movieFile));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line);
                            content.append("\n");
                        }
                        reader.close();
                        movieList.add(new Movie(Integer.parseInt(movieFile.getName().replaceAll("movie","").replaceAll(".txt","")),content.toString(), movieFile.getName(), ""));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public void deleteAllNotes() {
        List<String> noteList = new ArrayList<>();
        File notesDirectory = getNotesDirectory();
        if (notesDirectory.exists() && notesDirectory.isDirectory()) {
            File[] noteFiles = notesDirectory.listFiles();
            if (noteFiles != null) {
                for (File noteFile : noteFiles) {
                    if (noteFile.exists()) {
                        noteFile.delete();
                    }
                }
            }
        }
    }

    public void removeById(int id){
        List<String> noteList = new ArrayList<>();
        File notesDirectory = getNotesDirectory();
        if (notesDirectory.exists() && notesDirectory.isDirectory()) {
            File[] noteFiles = notesDirectory.listFiles();
            if (noteFiles != null) {
                for (File noteFile : noteFiles) {
                    if (noteFile.exists() && noteFile.getName()
                            .replaceAll("note","")
                            .replaceAll(".txt","").equalsIgnoreCase(id + "")) {
                        noteFile.delete();
                    }
                }
            }
        }
    }

    public void delete(String filename) {
        File notesDirectory = getNotesDirectory();
        if (notesDirectory.exists() && notesDirectory.isDirectory()) {
            File noteFile = new File(notesDirectory, filename);
            if (noteFile.exists()) {
                noteFile.delete();
            }
        }
    }

    private File getNotesDirectory() {
        return new File(context.getFilesDir(), NOTES_DIRECTORY);
    }

}