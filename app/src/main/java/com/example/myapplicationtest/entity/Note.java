package com.example.myapplicationtest.entity;

import java.util.Date;

public class Note {
    private int id;
    private String titre;
    private String text;
    private String fileName;
    private Date lastChange;

    public Note(int id, String titre, String text, String fileName, Date lastChange){
        this.id = id;
        this.titre = titre;
        this.text = text;
        this.fileName = fileName;
        this.lastChange = lastChange;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", text='" + text + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lastChange=" + lastChange +
                '}';
    }
}
