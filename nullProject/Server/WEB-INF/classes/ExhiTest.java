import com.projectl.dao.ExhiDAO;
import com.projectl.vo.Exhi;


public class ExhiTest {

	public ExhiTest(){
		ExhiDAO dao = new ExhiDAO();
		
		Exhi exhi = new Exhi(1,"exhiname",1);
		exhi.setExhi_no(1);
		dao.getExhiData(exhi);
		System.out.println(exhi);
		
	}
	
	public static void main(String[] args) {
		new ExhiTest();
	}

}
