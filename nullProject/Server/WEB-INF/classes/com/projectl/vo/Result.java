package com.projectl.vo;

import java.util.ArrayList;

public class Result {

	//json?��?��?���? ArrayList컬렉?��?�� 출력방식?�� �?경하�? ?��?�� ?��?�� [?��?�� {�?
		private int count; //몇개?�� 값을 ?��?��?��?�� return ?��?���? ?���?
		private String status; // ?��?�� ?��공과 ?��?�� ?���?
		private ArrayList<Exhi> list;
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Exhi> getList() {
		return list;
	}

	public void setList(ArrayList<Exhi> list) {
		this.list = list;
	}



	@Override
	public String toString() {
		return "Result [count=" + count + ", status=" + status + ", list=" + list + "]";
	}

}
