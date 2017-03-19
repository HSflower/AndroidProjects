package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.example.hong_inseon.projectlouvre.dao.Museum;
import com.example.hong_inseon.projectlouvre.dao.MuseumDAO;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LikeMuseum extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String[] name1, name2, Image, rating;

    ListView list;
    ListViewAdapterMuseum listh;
    ArrayList<Museum> arraylist = new ArrayList<Museum>();
    MuseumDAO msDao;
    Museum msData;
    private int men;
    // 미술관 리스트 선택
    private static final String msListSQL = "select * from museum order by ms_no desc;";
    // 좋아요한 미술관 리스트 선택
    private static final String msLikeListSQL = "select * from liked order by ms_no desc;";
    // 해당하는 미술관 정보 선택
    private static final String msSelectSQL = "select * from museum where ms_no = ? ;";

    /*
     msData.setMs_no(rst.getString(1));
                msData.setMs_address(rst.getString(2));
                msData.setMs_phone(rst.getString(3));
                msData.setMs_url(rst.getString(4));
                msData.setMs_holiday(rst.getString(5));
                msData.setMs_operating(rst.getString(6));
                msData.setMs_name(rst.getString(7));
                msData.setMs_rating(rst.getString(8));
                msData.setMs_like(rst.getString(9));
                msData.setMs_Img(rst.getString(10));
                msData.setMs_exp(rst.getString(11));
     */
    String str =
            "[{'no':'1','address':adresscontent,'phone':'010','url':'http://www.com','holiday':'일요일','operating':'월요일','name':'슈퍼맨','rating':'5','like':'20','img':'/img/','exp':'2017-09-09'},"+
                    "{'no':'2','address':adresscontent,'phone':'010','url':'http://www.com','holiday':'일요일','operating':'월요일','name':'배트맨','rating':'1','like':'2','img':'/img/','exp':'2017-09-01'},"+
                    "{'no':'3','address':adresscontent,'phone':'010','url':'http://www.com','holiday':'일요일','operating':'월요일','name':'앤트맨','rating':'2.5','like':'0','img':'/img/','exp':'2017-01-09'}]";
    //''없이 그냥 입력해서 숫자 입력가능 - getInt로 받기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple);

        men = -1;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view3);
        navigationView.setNavigationItemSelectedListener(this);

        //ListView logic
        list = (ListView)findViewById(R.id.listViewTemple);

        //String sMessage = etMessage.getText().toString(); // 보내는 메시지를 받아옴
        String result = SendByHttp(msListSQL); // 메시지를 서버에 보냄

        //String[][] parsedData = jsonParserList(); // 받은 메시지를 json 파싱결과를 museum객체에 저장
        arraylist = jsonParserList(result);
        listh = new ListViewAdapterMuseum(this, arraylist);
        //tvRecvData.setText(result);	// 받은 메시지를 화면에 보여주r기

        list.setAdapter(listh);
    }
    /**
     * 서버에 데이터를 보내는 메소드, ServerUtil
     * @param msg
     * @return
     */
    private String SendByHttp(String msg) {

        if(msg == null)
            msg = "";

        String URL = "http://35.161.181.60/JSONServer.jsp";
        DefaultHttpClient client = new DefaultHttpClient();

        try {
			/* 체크할 값 서버로 전송 : 쿼리문? */
            HttpPost post = new HttpPost(URL+"?query="+msg);
			/* 지연시간 최대 3초 */
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 3000);

			/* 데이터 보낸 뒤 서버에서 데이터를 받아오는 과정 */
            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line = null;
            String result = "";
            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            client.getConnectionManager().shutdown();	// 연결 지연 종료
            return "";
        }
    }

    /**
     * 받은 JSON 객체를 파싱하는 메소드
     * @param pRecvServerPage
     * @return
     */
    public ArrayList jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        try {
            JSONArray jarray = new JSONArray(str);

            // 받아온 pRecvServerPage를 분석하는 부분
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출

                if(jObject != null) { //museum 데이터 객체에 파싱한 값 저장.
                    msData = new Museum();
                    msData.setMs_no(jObject.getString("no"));
                    msData.setMs_name(jObject.getString("name"));
                    msData.setMs_rating(jObject.getString("rating"));

                    arraylist.add(msData);
                }
            }

            // 분해 된 데이터를 확인하기 위한 부분
            for(int i=0; i<jarray.length(); i++){
                Log.i("JSON을 파싱한 데이터 출력해보기"+i+" : ", msData.toString());
            }
            return arraylist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.some, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, OptionP.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }//클릭했을시

    @Override
    public void onStop() {
        super.onStop();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cartid) {
            men = 0;
        } else if (id == R.id.profileid) {
            men = 1;
        } else if (id == R.id.heartid) {
            men = 2;
        } else if (id == R.id.templeid) {
            men = 3;
        }
        Intent data = new Intent();
        data.putExtra("value1", men);
        setResult(1,data);
        this.finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
