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
import com.example.myapplicationtest.activity.food.FoodActivity;
import com.example.myapplicationtest.activity.food.IngredientActivity;
import com.example.myapplicationtest.activity.food.MealActivity;
import com.example.myapplicationtest.activity.food.ShoppingListActivity;
import com.example.myapplicationtest.activity.food.UberEatActivity;
import com.example.myapplicationtest.activity.gym.GymActivity;
import com.example.myapplicationtest.activity.planner.PlannerActivity;

public class ButtonFoodAdapter extends BaseAdapter {

    private Context context;

    private String[] buttonNames = {
            "Shopping list", "Uber Eat", "Meals", "Ingredients", "Back"
    };
    /*private String[] buttonNames = {
            "Notes", "Watchlist", "Raccoon", "Food", "Planner", "Reminders"
    };*/

    public ButtonFoodAdapter(Context context) {
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
                        intent = new Intent(context, ShoppingListActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 1:
                        intent = new Intent(context, UberEatActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 2:
                        intent = new Intent(context, MealActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 3:
                        intent = new Intent(context, IngredientActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 4:
                        intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    /*case 0:
                        intent = new Intent(context, NotesActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 1:
                        intent = new Intent(context, WatchlistActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 2:
                        intent = new Intent(context, RaccoonActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 3:
                        intent = new Intent(context, FoodActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 4:
                        intent = new Intent(context, PlannerActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
                    case 5:
                        intent = new Intent(context, RemindersActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;*/
                }
            }
        });

        return convertView;
    }
}
