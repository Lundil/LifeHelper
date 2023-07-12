package com.example.myapplicationtest.entity.food;

import com.example.myapplicationtest.entity.gym.Exercice;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class WorkoutPart {

    private Long id;

    private Long serie;

    private Long rep;

    private Long poids;

    private Exercice exo;

    public WorkoutPart() {
        // Default constructor required for calls to DataSnapshot.getValue(Ingredient.class)
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSerie() {
        return serie;
    }

    public void setSerie(Long serie) {
        this.serie = serie;
    }

    public Long getRep() {
        return rep;
    }

    public void setRep(Long rep) {
        this.rep = rep;
    }

    public Long getPoids() {
        return poids;
    }

    public void setPoids(Long poids) {
        this.poids = poids;
    }

    public Exercice getExo() {
        return exo;
    }

    public void setExo(Exercice exo) {
        this.exo = exo;
    }

    @Override
    public String toString() {
        return "WorkoutPart{" +
                "id=" + id +
                ", serie=" + serie +
                ", rep=" + rep +
                ", poids=" + poids +
                ", exo=" + exo +
                '}';
    }
}
