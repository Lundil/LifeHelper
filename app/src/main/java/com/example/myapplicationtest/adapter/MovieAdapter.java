package com.example.myapplicationtest.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.entity.Movie;
import com.example.myapplicationtest.factory.MovieFactory;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter {

    private Context mContext;
    private List<Movie> movieList = new ArrayList<>();

    public MovieAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public MovieAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mContext = context;
    }

    public MovieAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
        mContext = context;
    }

    public MovieAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Object[] objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }

    public MovieAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        mContext = context;
    }

    public MovieAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_movie,parent,false);

        movieList = new MovieFactory(mContext).getAllMovies();
        Movie currentMovie = movieList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.textViewMovie);
        name.setText(currentMovie.getTitre());

        ImageView image = (ImageView) listItem.findViewById(R.id.imageViewMovie);
        image.setImageURI(Uri.parse(currentMovie.getImage()));

        return listItem;
    }


}
