package com.example.whererhyu;

import java.io.Serializable;

public class Rest_Notice_Item implements Serializable{
	int pnum;
	String title;
	String time;
	String cont;
	boolean isphoto;
	byte[] photo;
	
	public Rest_Notice_Item(int pnum, String title, String time, String cont) {
		// TODO Auto-generated constructor stub
		this.pnum = pnum;
		this.title = title;
		this.time = time;
		this.cont = cont;
		this.isphoto = false;
		this.photo = null;
	}
	
	public Rest_Notice_Item(int pnum, String title, String time, String cont, byte[] photo) {
		// TODO Auto-generated constructor stub
		this.pnum = pnum;
		this.title = title;
		this.time = time;
		this.cont = cont;
		this.isphoto = true;
		this.photo = new byte[photo.length];
		System.arraycopy(photo, 0, this.photo, 0, photo.length);
	}

}
