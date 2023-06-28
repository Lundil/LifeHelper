package com.example.myapplicationtest.factory;


import android.content.Context;

import com.example.myapplicationtest.entity.Note;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NotesFactory {
    private static final String NOTES_DIRECTORY = "notes";
    private Context context;

    public NotesFactory(Context context) {
        this.context = context;
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

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();
        try {
            File notesDirectory = getNotesDirectory();
            if (notesDirectory.exists() && notesDirectory.isDirectory()) {
                File[] noteFiles = notesDirectory.listFiles();
                if (noteFiles != null) {
                    for (File noteFile : noteFiles) {
                        StringBuilder content = new StringBuilder();
                        BufferedReader reader = new BufferedReader(new FileReader(noteFile));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line);
                            content.append("\n");
                        }
                        reader.close();
                        noteList.add(new Note(Integer.parseInt(noteFile.getName().replaceAll("note","").replaceAll(".txt","")),content.toString().length() <= 5 ? content.toString() : content.toString().substring(0,5),content.toString(), noteFile.getName(), new Date()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(noteList, new Comparator<Note>() {
            public int compare(Note n1, Note n2) {
                return n1.getLastChange().compareTo(n2.getLastChange());
            }
        });
        return noteList;
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