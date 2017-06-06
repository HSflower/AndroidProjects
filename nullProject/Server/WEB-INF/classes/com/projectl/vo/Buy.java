package com.projectl.vo;

public class Buy {
	private String ms_no;
	private String ex_no;
	private String ex_img;
	private String buy_type;
	
	public Buy(String nameExhibit, String nameMuseum, String image, String buy_type) {
        this.ms_no = nameExhibit;
        this.ex_no = nameMuseum;
        this.ex_img = image;
        this.buy_type = buy_type;
    }
    
	public Buy() {}
    
	public String getMs_no() {
		return ms_no;
	}
	public void setMs_no(String ms_no) {
		this.ms_no = ms_no;
	}
	public String getEx_no() {
		return ex_no;
	}
	public void setEx_no(String ex_no) {
		this.ex_no = ex_no;
	}
	public String getEx_img() {
		return ex_img;
	}
	public void setEx_img(String ex_img) {
		this.ex_img = ex_img;
	}
	public String getBuy_type() {
		return buy_type;
	}
	public void setBuy_type(String buy_type) {
		this.buy_type = buy_type;
	}    
}
