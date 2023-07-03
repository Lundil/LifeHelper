package com.example.myapplicationtest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.activity.movies.WatchlistActivity;
import com.example.myapplicationtest.activity.notes.NotesActivity;
import com.example.myapplicationtest.activity.raccoons.RaccoonActivity;
import com.example.myapplicationtest.entity.Movie;
import com.example.myapplicationtest.factory.MovieFactory;
import com.example.myapplicationtest.utils.JSONReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(currentMovie.getTitre());
        strBuilder.append("\n");
        StringBuilder dispos = new StringBuilder();
        if(null != currentMovie.getApple() && currentMovie.getApple().booleanValue()){
            dispos.append("A ");
        }
        if(null != currentMovie.getDisney() && currentMovie.getDisney().booleanValue()){
            dispos.append("D ");
        }
        if(null != currentMovie.getPrime() && currentMovie.getPrime().booleanValue()){
            dispos.append("P ");
        }
        if(!dispos.toString().isEmpty()){
            strBuilder.append(dispos.toString().trim());
        }
        name.setText(strBuilder.toString());
        ImageView image = (ImageView) listItem.findViewById(R.id.imageViewMovie);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    new MovieAdapter.DownloadImageTask(image).execute(currentMovie.getPosterPath());
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


        Button delete = (Button) listItem.findViewById(R.id.deleteViewMovie);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieFactory movies = new MovieFactory(mContext);
                movies.delete("movie"+currentMovie.getId()+".json");
                Intent intent = new Intent(mContext, WatchlistActivity.class);
                mContext.startActivity(intent);
            }

        });

        image.setImageURI(Uri.parse(currentMovie.getPosterPath()));

        return listItem;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageDrawable(null);
            bmImage.setImageBitmap(result);
        }
    }
}
