package com.example.myapplicationtest.entity.gym;

import com.example.myapplicationtest.entity.food.WorkoutPart;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Workout {

    private Long id;
    private String name;
    private String type;
    private List<WorkoutPart> listWorkoutPart;

    public Workout() {
        // Default constructor required for calls to DataSnapshot.getValue(Ingredient.class)
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<WorkoutPart> getListWorkoutPart() {
        return listWorkoutPart;
    }

    public void setListWorkoutPart(List<WorkoutPart> listWorkoutPart) {
        this.listWorkoutPart = listWorkoutPart;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", listWorkoutPart=" + listWorkoutPart +
                '}';
    }
}
