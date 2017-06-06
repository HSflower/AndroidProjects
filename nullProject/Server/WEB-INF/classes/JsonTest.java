import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonIOException;
import com.mysql.jdbc.log.Log;
import com.projectl.vo.Museum;
//["{ms_no='null', ms_address='��õ������', ms_phone='', ms_url='http:\/\/www.inu.ac.kr', ms_holiday='', ms_operating='', ms_name='��õ���б�', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='����� ���ʱ� ���μ�ȯ�� 2406\r\n', ms_phone='02-580-1300', ms_url='http:\/\/www.sac.or.kr\/space\/info\/hangaram.jsp\r\n', ms_holiday='�ſ� �������� ������', ms_operating='3~10��(11~20)11��~2��(11~19)', ms_name='���������� �Ѱ����̼���\r\n', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='����� ���α� �ξϵ� 201 ����', ms_phone='02-395-0100', ms_url='http:\/\/www.seoulmuseum.org\/nr2\/index.php\r\n', ms_holiday='���� ������', ms_operating='������(10:30~17:30),������(11~18)', ms_name='����̼���', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='���������� ������ ������ 481\r\n', ms_phone='042-601-7894', ms_url='https:\/\/www.science.go.kr\/\r\n', ms_holiday='���� ������, ���� ������ ������, 1�� 1��, ����,', ms_operating='9:30~17:50', ms_name='�����߾Ӱ��а�\r\n', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='����� ��걸 ������� 137', ms_phone='02-2077-9000', ms_url='https:\/\/www.museum.go.kr\/site\/main\/home', ms_holiday='1�� 1��, ����, �߼�', ms_operating='��ȭ���(9~18)����(9~21)��,����(9~19)', ms_name='�����߾ӹڹ���', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='����� �߱� ������� 99', ms_phone='02-2022-0600', ms_url='https:\/\/www.mmca.go.kr\/main.do\r\n', ms_holiday='���� ������, 1�� 1��', ms_operating='10~19(��, ��� 21��)', ms_name='�������� �̼��� �����ð�', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='����� ���α� ��û�� 30', ms_phone='02-3701-9500', ms_url='https:\/\/www.mmca.go.kr\/main.do\r\n', ms_holiday='1�� 1��, ����, �߼�', ms_operating='10~19(��, ��� 21��)', ms_name='�������� �̼��� �����', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='��⵵ ��õ�� ����� 313\r\n', ms_phone='02-2188-6000', ms_url='https:\/\/www.mmca.go.kr\/main.do', ms_holiday='0000-00-00', ms_operating='10~19(��, ��� 21��)', ms_name='�������� �̼��� ��õ��', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='����Ư���� ���ʱ� ����3�� ���μ�ȯ�� 2406', ms_phone='2', ms_url='www.sac.or.kr', ms_holiday='0000-00-00', ms_operating='0000-00-00', ms_name='������ ����', ms_rating='0', ms_like='0', ms_Img='', ms_exp='null'}"]
public class JsonTest {
	static Museum msData;
    static ArrayList<Museum> arraylist = new ArrayList<Museum>();
	public static void main(String[] args) throws IOException {
		
		String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE";
        DefaultHttpClient client = new DefaultHttpClient();
		//Scanner scan = new Scanner(System.in);
        HttpPost post = new HttpPost(URL+"/getJsonMuseumList.jsp");
		/* �����ð� �ִ� 3�� */
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);

		/* ������ ���� �� �������� �����͸� �޾ƿ��� ���� */
        HttpResponse response = client.execute(post);
		BufferedReader bufreader =
				new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
	    String line = null;
	    String resultString = "";
	    while ((line = bufreader.readLine()) != null) {
	        resultString += line;
	    }
	    System.out.println(resultString);
	    arraylist = jsonParserList(resultString);
	    System.out.println("array:" + arraylist);
	}
	

    /**
     * ���� JSON ��ü�� �Ľ��ϴ� �޼ҵ�
     * @param pRecvServerPage
     * @return
     */
    public static ArrayList jsonParserList(String pRecvServerPage) {
        try {
        	JSONObject jObject = new JSONObject();
            jObject.get("museums");
        	JSONArray jarray = new JSONArray();
        	jarray.add(jObject);
            System.out.println("check:"+jarray);
            // �޾ƿ� pRecvServerPage�� �м��ϴ� �κ�
            for (int i = 0; i < jarray.size(); i++) {
            	//System.out.println(jarray.get(i));

                if(jObject != null) { //museum ������ ��ü�� �Ľ��� �� ����.
                    msData = new Museum();
                    msData.setMs_no((String) jObject.get("no"));
                    msData.setMs_name((String) jObject.get("name"));
                    msData.setMs_rating((String) jObject.get("rating"));
                    msData.setMs_exp((String) jObject.get("exp"));
                    msData.setMs_url((String) jObject.get("url"));
                    msData.setMs_like((String) jObject.get("like"));
                    msData.setMs_address((String) jObject.get("address"));
                    msData.setMs_holiday((String) jObject.get("holiday"));
                    msData.setMs_Img((String) jObject.get("img"));
                    msData.setMs_operating((String) jObject.get("operating"));
                    msData.setMs_phone((String) jObject.get("phone"));
                    arraylist.add(msData);
                }
            }

            return arraylist;
        } catch (JsonIOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
