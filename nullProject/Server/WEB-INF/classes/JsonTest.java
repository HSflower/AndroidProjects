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
//["{ms_no='null', ms_address='인천광역시', ms_phone='', ms_url='http:\/\/www.inu.ac.kr', ms_holiday='', ms_operating='', ms_name='인천대학교', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='서울시 서초구 남부순환로 2406\r\n', ms_phone='02-580-1300', ms_url='http:\/\/www.sac.or.kr\/space\/info\/hangaram.jsp\r\n', ms_holiday='매월 마지막주 월요일', ms_operating='3~10월(11~20)11월~2월(11~19)', ms_name='예술의전당 한가람미술관\r\n', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='서울시 종로구 부암동 201 번지', ms_phone='02-395-0100', ms_url='http:\/\/www.seoulmuseum.org\/nr2\/index.php\r\n', ms_holiday='매주 월요일', ms_operating='동절기(10:30~17:30),하절기(11~18)', ms_name='서울미술관', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='대전광역시 유성구 대덕대로 481\r\n', ms_phone='042-601-7894', ms_url='https:\/\/www.science.go.kr\/\r\n', ms_holiday='매주 월요일, 법정 공휴일 다음날, 1월 1일, 설날,', ms_operating='9:30~17:50', ms_name='국립중앙과학관\r\n', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='서울시 용산구 서빙고로 137', ms_phone='02-2077-9000', ms_url='https:\/\/www.museum.go.kr\/site\/main\/home', ms_holiday='1월 1일, 설날, 추석', ms_operating='월화목금(9~18)수토(9~21)일,공휴(9~19)', ms_name='국립중앙박물관', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='서울시 중구 세종대로 99', ms_phone='02-2022-0600', ms_url='https:\/\/www.mmca.go.kr\/main.do\r\n', ms_holiday='매주 월요일, 1월 1일', ms_operating='10~19(수, 토는 21시)', ms_name='국립현대 미술관 덕수궁관', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='서울시 종로구 삼청로 30', ms_phone='02-3701-9500', ms_url='https:\/\/www.mmca.go.kr\/main.do\r\n', ms_holiday='1월 1일, 설날, 추석', ms_operating='10~19(수, 토는 21시)', ms_name='국립현대 미술관 서울관', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='경기도 과천시 광명로 313\r\n', ms_phone='02-2188-6000', ms_url='https:\/\/www.mmca.go.kr\/main.do', ms_holiday='0000-00-00', ms_operating='10~19(수, 토는 21시)', ms_name='국립현대 미술관 과천관', ms_rating='0', ms_like='null', ms_Img='', ms_exp='null'}","{ms_no='null', ms_address='서울특별시 서초구 서초3동 남부순환로 2406', ms_phone='2', ms_url='www.sac.or.kr', ms_holiday='0000-00-00', ms_operating='0000-00-00', ms_name='예술의 전당', ms_rating='0', ms_like='0', ms_Img='', ms_exp='null'}"]
public class JsonTest {
	static Museum msData;
    static ArrayList<Museum> arraylist = new ArrayList<Museum>();
	public static void main(String[] args) throws IOException {
		
		String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE";
        DefaultHttpClient client = new DefaultHttpClient();
		//Scanner scan = new Scanner(System.in);
        HttpPost post = new HttpPost(URL+"/getJsonMuseumList.jsp");
		/* 지연시간 최대 3초 */
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);

		/* 데이터 보낸 뒤 서버에서 데이터를 받아오는 과정 */
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
     * 받은 JSON 객체를 파싱하는 메소드
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
            // 받아온 pRecvServerPage를 분석하는 부분
            for (int i = 0; i < jarray.size(); i++) {
            	//System.out.println(jarray.get(i));

                if(jObject != null) { //museum 데이터 객체에 파싱한 값 저장.
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
