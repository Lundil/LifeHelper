package com.example.myapplicationtest.entity;

import java.util.Date;

public class Movie {
    private int id;
    private String titre;
    private String text;

    private String image;

    public Movie(int id, String titre, String text, String image){
        this.id = id;
        this.titre = titre;
        this.text = text;
        this.image = image;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
