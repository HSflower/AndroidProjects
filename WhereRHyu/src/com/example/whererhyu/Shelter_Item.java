package com.example.whererhyu;

import java.io.Serializable;

public class Shelter_Item implements Serializable{
	
	public String id;
	public String name;
	public float latitude; // 위도
	public float longitude; // 경도
	int updown;
	
	public Shelter_Item(String id, String name, float latitude, float longitude, int updown) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.updown = updown;
	}

}
