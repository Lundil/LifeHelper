package com.example.myapplicationtest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.activity.MainActivity;
import com.example.myapplicationtest.activity.chill.ChillActivity;
import com.example.myapplicationtest.activity.chill.TripActivity;
import com.example.myapplicationtest.activity.chill.WatchlistActivity;
import com.example.myapplicationtest.activity.gym.ExerciceActivity;
import com.example.myapplicationtest.activity.gym.PlanningActivity;
import com.example.myapplicationtest.activity.gym.WorkoutActivity;

public class ButtonChillAdapter extends BaseAdapter {

    private Context context;

    private String[] buttonNames = {
            "Watchlist", "Trips", "Back"
    };

    public ButtonChillAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return buttonNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.button_item, null);
        }

        Button button = convertView.findViewById(R.id.button);
        button.setText(buttonNames[position]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                // Gérer l'événement du clic ici
                switch (position) {
                    case 0:
                        intent = new Intent(context, WatchlistActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 1:
                        intent = new Intent(context, TripActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 2:
                        intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                }
            }
        });

        return convertView;
    }
}
