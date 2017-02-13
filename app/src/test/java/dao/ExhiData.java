package dao;

import java.util.ArrayList;

/**
 * Created by samsung on 2016-12-03.
 */

public class ExhiData {

    private int exhi_no;
    private String exhi_intro;
    private String exhi_start;
    private String exhi_finish;
    private String exhi_name;
    private String exhi_pic1;
    private String exhi_pic2;
    private String ms_no;

    ArrayList<Workbook> workbook;
    public ArrayList<Workbook> getWorkbooks() {
        return workbook;
    }
    public void setWorkbooks(ArrayList<Workbook> workbook) {
        this.workbook = workbook;
    }

    ArrayList<Workbook> audio;
    public ArrayList<Workbook> getAudios() {
        return audio;
    }
    public void setAudios(ArrayList<Workbook> audio) {
        this.audio = audio;
    }
    //전시번호가 같은지 확인하고, 참거짓반환?

    public ExhiData(){}

    @Override
    public String toString() {
        return "ExhiData [" +
                "exhi_no='" + exhi_no + '\'' +
                ", exhi_intro='" + exhi_intro + '\'' +
                ", exhi_start='" + exhi_start + '\'' +
                ", exhi_finish='" + exhi_finish + '\'' +
                ", exhi_name='" + exhi_name + '\'' +
                ", exhi_pic1='" + exhi_pic1 + '\'' +
                ", exhi_pic2='" + exhi_pic2 + '\'' +
                ']';
    }

    public int getExhi_no() {
        return exhi_no;
    }

    public void setExhi_no(int exhi_no) {
        this.exhi_no = exhi_no;
    }

    public String getExhi_intro() {
        return exhi_intro;
    }

    public void setExhi_intro(String exhi_intro) {
        this.exhi_intro = exhi_intro;
    }

    public String getExhi_start() {
        return exhi_start;
    }

    public void setExhi_start(String exhi_start) {
        this.exhi_start = exhi_start;
    }

    public String getExhi_finish() {
        return exhi_finish;
    }

    public void setExhi_finish(String exhi_finish) {
        this.exhi_finish = exhi_finish;
    }

    public String getExhi_name() {
        return exhi_name;
    }

    public void setExhi_name(String exhi_name) {
        this.exhi_name = exhi_name;
    }

    public String getExhi_pic1() {
        return exhi_pic1;
    }

    public void setExhi_pic1(String exhi_pic1) {
        this.exhi_pic1 = exhi_pic1;
    }

    public String getExhi_pic2() {
        return exhi_pic2;
    }

    public void setExhi_pic2(String exhi_pic2) {
        this.exhi_pic2 = exhi_pic2;
    }

}
