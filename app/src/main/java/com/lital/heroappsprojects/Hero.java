package com.lital.heroappsprojects;

import com.google.gson.Gson;

import java.util.List;

public class Hero {

    private final String image;
    private final List<String> abilities;
    private final String title;
    private boolean isFavorite;
    public Hero(String image, List<String> abilities, String title) {
        this.image = image;
        this.abilities = abilities;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getImage() {
        return image;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
