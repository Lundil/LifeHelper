package com.example.myapplicationtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplicationtest.factory.NotesFactory;
import com.example.myapplicationtest.R;
import com.example.myapplicationtest.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ArrayAdapter {

    private Context mContext;
    private List<Note> noteList = new ArrayList<>();

    public NoteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public NoteAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mContext = context;
    }

    public NoteAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
        mContext = context;
    }

    public NoteAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Object[] objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }

    public NoteAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        mContext = context;
    }

    public NoteAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_note,parent,false);

        noteList = new NotesFactory(mContext).getAllNotes();
        Note currentNote = noteList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.textViewNote);
        name.setText(currentNote.getTitre());

        return listItem;
    }


}
