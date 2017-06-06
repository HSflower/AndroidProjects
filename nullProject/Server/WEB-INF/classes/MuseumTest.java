import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.projectl.dao.MuseumDAO;
import com.projectl.vo.Museum;


public class MuseumTest {

	public MuseumTest(){
		MuseumDAO dao = new MuseumDAO();
		
		//Museum m1 = new Museum("1","Ms name");
		//m1.setMs_no("1");
		//dao.getMuseumData(m1);
//		System.out.println("m1 started");
//		System.out.println(m1);
//		System.out.println("m1 finished ");
		
		JSONObject json = dao.getMuseumList();
		//JSONObject result = dao.getLikedMuseumList("1");
		//System.out.println(result);
		System.out.println(json);
	}
	
	
	public static void main(String[] args) {
		new MuseumTest();
	}

}
