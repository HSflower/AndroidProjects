package com.example.hong_inseon.projectlouvre;

/**
 * Created by 박명준 on 2017-02-23.
 */

public class Museum {
    private String name1, name2;
    int Rating, Image;

    public Museum(String name1, int Rating, String name2, int Image) {
        this.name1 = name1; this.Rating = Rating; this.name2 = name2; this.Image = Image;
    }

    public String getNameM() {
        return this.name1;
    }

    public int getNameR() {
        return this.Rating;
    }

    public String getNameA() {
        return this.name2;
    }

    public int getImage() {
        return Image;
    }
}