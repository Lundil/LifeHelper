package com.example.myapplicationtest.entity.gym;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Exercice {

    private Long id;
    private String name;
    private String type;
    private String image;

    private String tips;

    public Exercice() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return "Exercice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
}
