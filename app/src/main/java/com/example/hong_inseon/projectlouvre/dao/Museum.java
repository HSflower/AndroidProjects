package com.example.hong_inseon.projectlouvre.dao;

/**
 * Created by 박명준 on 2017-02-23.
 */

public class Museum {

    private String ms_no; //박물관 번호
    private String ms_address; //박물관 주소
    private String ms_phone; //박물관 전화번호
    private String ms_webpage; //박물관 홈페이지
    private String ms_holiday; //박물관 휴일
    private String ms_operating; // 박물관 운영시간
    private String ms_name; //박물관 이름
    private String ms_rating, Image;  // 박물관 평점, 박물관 이미지 변수

    public Museum(String ms_name, String Rating, String ms_address, String Image) {
        this.ms_name = ms_name; this.ms_rating = Rating; this.ms_address = ms_address; this.Image = Image;
    }
    public Museum() {

    }

    public void setMs_no(String ms_no) {
        this.ms_no = ms_no;
    }

    public String getMs_no(){
        return ms_no;
    }

    public String getMs_address() {
        return ms_address;
    }

    public void setMs_address(String ms_address) {
        this.ms_address = ms_address;
    }

    public String getMs_phone() {
        return ms_phone;
    }

    public void setMs_phone(String ms_phone) {
        this.ms_phone = ms_phone;
    }

    public String getMs_webpage() {
        return ms_webpage;
    }

    public void setMs_webpage(String ms_webpage) {
        this.ms_webpage = ms_webpage;
    }

    public String getMs_holiday() {
        return ms_holiday;
    }

    public void setMs_holiday(String ms_holiday) {
        this.ms_holiday = ms_holiday;
    }

    public String getMs_operating() {
        return ms_operating;
    }

    public void setMs_operating(String ms_operating) {
        this.ms_operating = ms_operating;
    }

    public String getMs_name() {
        return ms_name;
    }

    public void setMs_name(String ms_name) {
        this.ms_name = ms_name;
    }

    public String getMs_rating() {
        return ms_rating;
    }

    public void setMs_rating(String ms_rating) {
        this.ms_rating = ms_rating;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public String toString() {
        return "MuseumData{" +
                "ms_no=" + ms_no +
                ", ms_address='" + ms_address + '\'' +
                ", ms_phone='" + ms_phone + '\'' +
                ", ms_webpage='" + ms_webpage + '\'' +
                ", ms_holiday='" + ms_holiday + '\'' +
                ", ms_operating='" + ms_operating + '\'' +
                ", ms_name='" + ms_name + '\'' +
                ", ms_rating='" + ms_rating + '\'' +
                '}';
    }
    /*
    ArrayList<ExhiData> exhidata;

	public ArrayList<ExhiData> getExhidata() {
		return exhidata;
	}
	public void setExhidata(ArrayList<ExhiData> exhidata) {
		this.exhidata = exhidata;
	}
     */
}