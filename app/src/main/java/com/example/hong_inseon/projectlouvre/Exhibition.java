package com.example.hong_inseon.projectlouvre;

/**
 * Created by 박명준 on 2017-02-23.
 */
public class Exhibition {
    private String nameM, nameW, nameP; // 전시회 이름, 전시회 작품 이름, 전시 기간 변수
    private int P;  // 이미지 저장 상수 변수

    public Exhibition(String nameM, String nameW, String nameP, int P) {
        this.nameM = nameM;
        this.nameW = nameW;
        this.nameP = nameP;
        this.P = P;

    }

    public String getNameM() {
        return this.nameM;
    }
    //전시회 이름 받아오기

    public String getNameW() {
        return this.nameW;
    }
    //전시 작품 이름 받아오기

    public String getNameP() {
        return this.nameP;
    }
    //전시 기간 받아오기

    public int getImage() {
        return this.P;
    }
    //전시회 이미지 받아오기
}