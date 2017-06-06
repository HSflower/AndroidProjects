package com.projectl.vo;

public class Notice {
	private int notice_no;
	private int ms_no;
	private String title;
	private String context;
	private String date;
	private String writer;
	
	public Notice() {}
	
	public Notice(int notice_no, int ms_no, String title, String contet, String Date, String writer) {
        this.notice_no = notice_no;
		this.ms_no = ms_no;
        this.title= title;
        this.context = context;
        this.date = date;
        this.writer = writer;
	}

	public int getNotice_no() {
		return notice_no;
	}

	public void setNotice_no(int notice_no) {
		this.notice_no = notice_no;
	}

	public int getMs_no() {
		return ms_no;
	}

	public void setMs_no(int ms_no) {
		this.ms_no = ms_no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
		
	
}

