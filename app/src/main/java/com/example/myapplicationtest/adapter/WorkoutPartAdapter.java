package com.example.myapplicationtest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.entity.food.Ingredient;
import com.example.myapplicationtest.entity.food.WorkoutPart;
import com.example.myapplicationtest.entity.gym.Workout;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WorkoutPartAdapter extends ArrayAdapter {

    private Context mContext;
    private List<Ingredient> ingredientList = new ArrayList<>();

    public WorkoutPartAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public WorkoutPartAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mContext = context;
    }

    public WorkoutPartAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
        mContext = context;
    }

    public WorkoutPartAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Object[] objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }

    public WorkoutPartAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        mContext = context;
    }

    public WorkoutPartAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_workout_part,parent,false);

        WorkoutPart currentWorkoutPart = (WorkoutPart) getItem(position);
        TextView textViewIngredient = (TextView) listItem.findViewById(R.id.textViewWorkoutPart);
        textViewIngredient.setText("TODO affichage workoutpart");

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
                InputStream in = new URL(urldisplay).openStream();
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
