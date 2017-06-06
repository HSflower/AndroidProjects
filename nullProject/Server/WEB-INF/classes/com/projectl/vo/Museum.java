package com.projectl.vo;

/**
* Museum Table
* �ڹ��� ��ȣ : int,
* �ڹ��� �ּ�, ��ȭ��ȣ, Ȩ������, ����, ��ð�, �̸� : String( varchar, char)
* �ڹ��� ���� : float
* �ڹ��� �̹���, exp : string( varchar, char )
 */

public class Museum {

	private String ms_no; //�ڹ��� ��ȣ int
    private String ms_address; //�ڹ��� �ּ�
    private String ms_phone; //�ڹ��� ��ȭ��ȣ
    private String ms_url; //�ڹ��� Ȩ������
    private String ms_holiday; //�ڹ��� ����
    private String ms_operating; // �ڹ��� ��ð�
    private String ms_name; //�ڹ��� �̸�
    private String ms_rating; //���� float
    private String ms_like; //���ƿ� int
    private String ms_Img;  // �ڹ��� �̹��� ����
    private String ms_exp; //
//	private int ms_no;
//	private String ms_address;
//	private String ms_phone;
//	private String ms_webpage;
//	private String ms_holiday;
//	private String ms_operating;
//	private String ms_name;
    /*
    ArrayList<ExhiData> exhidata;

	public ArrayList<ExhiData> getExhidata() {
		return exhidata;
	}
	public void setExhidata(ArrayList<ExhiData> exhidata) {
		this.exhidata = exhidata;
	}
     */
    
    /**
     * @param ms_no, String ms_address, String ms_phone, String ms_url, String ms_holiday, String ms_operating, String ms_name, String ms_rating, String ms_like, String ms_Image, String ms_exp
     * @return
     */
    public Museum(String ms_no, String ms_address, String ms_phone, String ms_url, String ms_holiday, String ms_operating, String ms_name, String ms_rating, String ms_like, String ms_Image, String ms_exp) {
        this.ms_no = ms_no;
        this.ms_address = ms_address;
        this.ms_phone = ms_phone;
        this.ms_url = ms_url;
        this.ms_holiday = ms_holiday;
        this.ms_operating = ms_operating;
        this.ms_name = ms_name;
        this.ms_rating = ms_rating;
        this.ms_like = ms_like;
        this.ms_Img = ms_Image;
        this.ms_exp = ms_exp;
    }

    //web test constructor by hs
	public Museum(String ms_no, String ms_address, String ms_phone, String ms_webpage, String ms_holiday,
			String ms_operating, String ms_name) {
		super();
		this.ms_no = ms_no;
		this.ms_address = ms_address;
		this.ms_phone = ms_phone;
		this.ms_url = ms_webpage;
		this.ms_holiday = ms_holiday;
		this.ms_operating = ms_operating;
		this.ms_name = ms_name;
	}
	// android test constructor by mj
    public Museum(String ms_name, String Rating, String ms_address, String Image) {
        this.ms_name = ms_name;
        this.ms_rating = Rating;
        this.ms_address = ms_address;
        this.ms_Img = Image;
    }
	public Museum(String ms_no, String ms_name) {
		super();
		this.ms_no = ms_no;
		this.ms_name = ms_name;
	}
	public Museum() {
		super();
	}
	
//	test toString() for web by hs
//	@Override
//	public String toString() {
//		return "Museum [ms_no=" + ms_no + ", ms_address=" + ms_address
//				+ ", ms_phone=" + ms_phone + ", ms_webpage=" + ms_webpage
//				+ ", ms_holiday=" + ms_holiday + ", ms_operating="
//				+ ms_operating + ", ms_name=" + ms_name + "]";
//	}
	
	@Override
    public String toString() {
        return "{'ms_no':'" + ms_no + '\'' +
                ", 'ms_address':'" + ms_address + '\'' +
                ", 'ms_phone':'" + ms_phone + '\'' +
                ", 'ms_url':'" + ms_url + '\'' +
                ", 'ms_holiday':'" + ms_holiday + '\'' +
                ", 'ms_operating':'" + ms_operating + '\'' +
                ", 'ms_name':'" + ms_name + '\'' +
                ", 'ms_rating':'" + ms_rating + '\'' +
                ", 'ms_like':'" + ms_like + '\'' +
                ", 'ms_Img':'" + ms_Img + '\'' +
                ", 'ms_exp':'" + ms_exp + '\'' +
                '}';
    }
	
	public String getMs_rating() {
        return ms_rating;
    }

    public void setMs_rating(String ms_rating) {
        this.ms_rating = ms_rating;
    }

    public String getMs_no() {
		return ms_no;
	}
	public void setMs_no(String ms_no) {
		// int�� �Է� ? string���� �Է�?
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
	public String getMs_url() {
		return ms_url;
	}
	public void setMs_url(String ms_url) {
		this.ms_url = ms_url;
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
	public void setMs_like(String ms_like) {
        this.ms_like = ms_like;
    }

    public String getMs_Img() {
        return ms_Img;
    }

    public void setMs_Img(String ms_Image) {
        this.ms_Img = ms_Image;
    }

    public String getMs_exp() {
        return ms_exp;
    }

    public void setMs_exp(String ms_exp) {
        this.ms_exp = ms_exp;
    }

	
}
