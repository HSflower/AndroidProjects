package com.example.hong_inseon.projectlouvre;

/**
 * Created by 박명준 on 2017-02-23.
 */

public class Museum {
    private String name1, name2; // 박물관 이름, 박물관 주소 변수
    int Rating, Image;  // 박물관 평점, 박물관 이미지 변수

    public Museum(String name1, int Rating, String name2, int Image) {
        this.name1 = name1; this.Rating = Rating; this.name2 = name2; this.Image = Image;
    }

    public String getNameM() {
        return this.name1;
    }
    //박물관 이름 받아오기

    public int getNameR() {
        return this.Rating;
    }
    //박물관 평점 받아오기

    public String getNameA() {
        return this.name2;
    }
    //박물관 주소 받아오기

    public int getImage() {
        return Image;
    }
    //박물관 이미지 받아오기
}