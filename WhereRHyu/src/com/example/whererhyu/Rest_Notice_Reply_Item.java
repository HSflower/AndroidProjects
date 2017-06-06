package com.example.whererhyu;

import java.io.Serializable;

public class Rest_Notice_Reply_Item implements Serializable{
	int num;
	String name;
	String time;
	String cont;
	
	public Rest_Notice_Reply_Item(int num, String name, String time, String cont) {
		// TODO Auto-generated constructor stub\
		this.num = num;
		this.name = name;
		this.time = time;
		this.cont = cont;
	}
}
