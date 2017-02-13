package dao;

import java.io.Serializable;
import java.util.ArrayList;

public class MuseumData implements Serializable {


	private static final long serialVersionUID = -4277909732788393669L;

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
				'}';
	}

	ArrayList<ExhiData> exhidata;

	public ArrayList<ExhiData> getExhidata() {
		return exhidata;
	}
	public void setExhidata(ArrayList<ExhiData> exhidata) {
		this.exhidata = exhidata;
	}

	//`ms_address` ,  `ms_phone` ,  `ms_webpage` ,  `ms_holiday` ,  `ms_operating` ,
	// `ms_name`
	private int ms_no;
	private String ms_address;
	private String ms_phone;
	private String ms_webpage;
	private String ms_holiday;
	private String ms_operating;
	private String ms_name;

	public int getMs_no() {
		return ms_no;
	}

	public void setMs_no(int ms_no) {
		this.ms_no = ms_no;
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

	//private ArrayList<Workbook> songs= new ArrayList<Workbook>();
//	public ArrayList<Workbook> getSongs() {
//		return songs;
//	}

//	public void setSongs(ArrayList<Workbook> songs) {
//		this.songs = songs;
//	}





}
