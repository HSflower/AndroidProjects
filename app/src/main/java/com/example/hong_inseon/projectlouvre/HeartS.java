package com.example.hong_inseon.projectlouvre;

/**
 * Created by 박명준 on 2017-02-26.
 */

public class HeartS {
    private String nameM, nameW, nameP;
    private int P;

    public HeartS(String nameM, String nameW, String nameP, int P) {
        this.nameM = nameM;
        this.nameW = nameW;
        this.nameP = nameP;
        this.P = P;

    }

    public String getNameM() {
        return this.nameM;
    }
    public String getNameW() {
        return this.nameW;
    }
    public String getNameP() {
        return this.nameP;
    }
    public int getImage() {
        return this.P;
    }

}