package com.projectl.vo;

import java.util.ArrayList;

public class Result {

	//json?˜•?‹?œ¼ë¡? ArrayListì»¬ë ‰?…˜?˜ ì¶œë ¥ë°©ì‹?„ ë³?ê²½í•˜ê¸? ?œ„?•´ ?ƒ?„± [?—?„œ {ë¡?
		private int count; //ëª‡ê°œ?˜ ê°’ì„ ?ŒŒ?‹±?•´?„œ return ?•˜?Š”ì§? ? •ë³?
		private String status; // ?ŒŒ?‹± ?„±ê³µê³¼ ?‹¤?Œ¨ ? •ë³?
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
