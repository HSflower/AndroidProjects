package com.example.whererhyu;

import java.io.Serializable;

public class Rest_Item implements Serializable{
	
	public String id;
	public String name;
	public float latitude;//위도
	public float longitude;//경도
	public float star_point;
	public int updown;
	
	public Rest_Item(String id,String name, float latitude, float longitude, float star_point, int updown) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.star_point = star_point;
		this.updown = updown;
	}
	
}
