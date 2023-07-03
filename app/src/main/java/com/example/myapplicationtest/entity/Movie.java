package com.example.myapplicationtest.entity;

import android.content.Context;

import com.example.myapplicationtest.factory.MovieFactory;

import java.util.Date;

public class Movie {
    private int id;
    private String titre;
    private String year;
    private String backdropPath;
    private String posterPath;
    private String tagline;
    private Boolean apple;
    private Boolean disney;
    private Boolean prime;

    public Movie(){}

    public Movie(Context c){
        this.id = new MovieFactory(c).getLastIdMovie();
    }

    public Movie(int id, String titre, String year, String backdropPath, String posterPath, String tagline, Boolean apple, Boolean disney, Boolean prime) {
        this.id = id;
        this.titre = titre;
        this.year = year;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.tagline = tagline;
        this.apple = apple;
        this.disney = disney;
        this.prime = prime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Boolean getApple() {
        return apple;
    }

    public void setApple(Boolean apple) {
        this.apple = apple;
    }

    public Boolean getDisney() {
        return disney;
    }

    public void setDisney(Boolean disney) {
        this.disney = disney;
    }

    public Boolean getPrime() {
        return prime;
    }

    public void setPrime(Boolean prime) {
        this.prime = prime;
    }
}
