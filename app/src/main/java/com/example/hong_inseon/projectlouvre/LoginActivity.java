//http://cocomo.tistory.com/387
//http://swalloow.tistory.com/60
//jsp서버연동 : http://blog.naver.com/PostView.nhn?blogId=abcdtyy456&logNo=220597953881

package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//import static android.app.Activity.RESULT_OK;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnJoin; //sign up button
    private Button btnLogin; //login button
    private CheckBox autoLogin;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    //Boolean loginChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        autoLogin = (CheckBox) findViewById(R.id.autoLogin);

        pref = getSharedPreferences("pref", 0);
        editor = pref.edit();


        //회원가입 버튼 누르면
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);

                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고 없으면 만들어서 사용
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                startActivityForResult(intent, 1000);
            }
        });

        //if autoLogin checked, get input
        if (pref.getBoolean("autoLogin", false)) {
            etEmail.setText(pref.getString("id", ""));
            etPassword.setText(pref.getString("pw", ""));
            autoLogin.setChecked(true);
            // goto LoginActivity
        }


        //set checkBoxListener
        // 자동 로그인 처리
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String id = etEmail.getText().toString();
                    String pw = etEmail.getText().toString();

                    editor.putString("id", id);
                    editor.putString("pw", pw);
                    editor.putBoolean("autoLogin", true);
                    editor.commit();
                } else {
                    //if unChecked, remove All
                    editor.clear();
                    editor.commit();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");

        if (requestCode == 1000 & resultCode == RESULT_OK) {
            Toast.makeText(LoginActivity.this, "회원 가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
            etEmail.setText(data.getStringExtra("email"));
        }
    }

    public void login_btn(View view) {
        //Toast.makeText(LoginActivity.this, "login successed", Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        //startActivity(intent);
        //finish();
        // 검사 추가해야 함

        // http 통신
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // 로그인 버튼 누르면 서버에 데이터 보내고 받기
        String sMessage = etEmail.getText().toString(); // 보내는 메시지 받아옴
        String result = SendByHttp(sMessage); // 메세지를 서버에 보냄, 데이터 보내고 받은 것을 result에 저장
        String[][] parsedData = jsonParserList(result); // 받은 메시지(데이터) 파싱

        if (parsedData[0][0].equals("succed")) {
            Toast.makeText(LoginActivity.this, "login successed", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private String SendByHttp(String msg) {
        if (msg == null) {
            msg = "";
        }

        // 웹서버 주소 설정
        String URL = "http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE/memberDB.jsp";
        DefaultHttpClient client = new DefaultHttpClient();

        try {
            /* 체크할 id와 pw값 서버로 전송 */
            HttpPost post = new HttpPost(URL + "?id=" + etEmail.getText().toString() + "&pw=" + etPassword.getText().toString());

            /* 데이터 보낸 뒤 서버에서 데이터를 받아오는 과정 */
            HttpResponse response = client.execute(post);

            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line = null;
            String result = "";

            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) { // 예외처리
            e.printStackTrace();
            //client.getConnectionManager().shutdown(); // 연결 지연 종료
            return "";
        }
    }

    // 받아온 데이터 파싱하는 함수
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage); // 받아온 데이터 확인

        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            // 받아온 pRecvServerPage를 분석하는 부분
            String[] jsonName = {"msg1", "msg2", "msg3"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }

            // 분해 된 데이터를 확인하기 위한 부분
            for (int i = 0; i < parseredData.length; i++) {
                Log.i("JSON을 분석한 데이터" + i + ":", parseredData[i][0]);
                Log.i("JSON을 분석한 데이터" + i + ":", parseredData[i][1]);
                Log.i("JSON을 분석한 데이터" + i + ":", parseredData[i][2]);
            }
            return parseredData; // 파싱한 데이터 넘김
        } catch (JSONException e) { // 예외처리
            e.printStackTrace();
            return null;
        }
    }
}




    /*

    //////????
        else {
            // if autoLogin unChecked
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            Boolean validation = loginValidation(email, password);

            if (validation) {
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                // save id, password to Database

                if (loginChecked) {
                    // if autoLogin Checked, save values
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.putBoolean("autoLogin", true);
                    editor.commit();
                }

                //goto LoginActivity
            } else {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                //goto LoginActivity
            }
        }


// 로그인 확인
    // Preference에서 받아온 정보와 일치하면 true반환
    // 저장된 정보가 없거나 일치하지 않으면 false 반환
    private boolean loginValidation(String id, String password) {
        if(pref.getString("id","").equals(id) && pref.getString("pw","").equals(password)) {
            // login success
            return true;
        } else if (pref.getString("id","").equals(null)){
            // sign in first
            Toast.makeText(LoginActivity.this, "Please Sign in first", Toast.LENGTH_LONG).show();
            return false;
        } else {
            // login failed
            return false;
        }
    }






    //

    private EditText etEmail;
    private EditText etPassword;
    //private Button btnRegist; //sign up button
    //private Button loginBtn; //Log in button
    CheckBox autoLogin;
    SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor;
    Boolean loginChecked;

    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.emailinput);
        etPassword = (EditText) findViewById(R.id.passwordInput);
        btnRegist = (Button) findViewById(R.id.signupBtn);
        autoLogin = (CheckBox) findViewById(R.id.autoLogin);
        loginBtn = (Button) findViewById(R.id.loginBtn);
    //}

    public void join_btn(View view){
        Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
        startActivity(intent);
        finish();
    }

    public void login_btn(View view){
        String sMessage = etEmail.getText().toString();
        String result = SendByHttp(sMessage);
        String[][] parsedData = jsonParserList(result); //data parsing
        if(parsedData[0][0].equals("succed")){
            Toast.makeText(LoginActivity.this, "login successed", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private String SendByHttp(String msg) {
        if (msg == null) {
            msg = "";
        }

        String URL = "http://자신의 아이피:8080/프로젝트명/~~~~.jsp";
        //자신의 웹서버 주소를 저장합니다.
        DefaultHttpClient client = new DefaultHttpClient();
        //HttpClient 통신을 합니다. Http 라이브러리가 필요합니다.
        try {
            HttpPost post = new HttpPost(URL + "?id=" + etEmail.getText().toString()+"&pw="+etPassword.getText().toString());
            //웹서버로 데이터를 전송합니다. 요번에 경우에는 get방식으로 데이터를 전송하였습니다.
            HttpResponse response = client.execute(post);
            //데이터를 보내고 바로 데이터 응답을 받습니다.
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            //받아온 데이터를 buffer에 넣습니다.
            String line = null;
            String result = "";

            while ((line = bufreader.readLine()) != null) {
                result += line;
            } //buffer를 읽어와서 result에 넣습니다.
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            client.getConnectionManager().shutdown();
//예외처리입니다.
            return "";
        }
    }

    //받아온 데이터를 파싱하는 부분입니다.
    public String[][] jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage); //받아온 데이터를 확인
        //안드로이드 하단에 쭉쭉 뭐 뜨는거 보이시면 그거 Log들인데 거기게 흔적을 남기는 겁니다. info에서 찾아보시면 찾으실 수 있습니다.
        try{ //아까말한 {"List":[{"data1":"sfasf".""data2:"sdfsdf"}]} 이형태를 분해하는 과정입니다.
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for(int i = 0; i<jArr.length();i++){
                json = jArr.getJSONObject(i);
                for (int j=0;j<jsonName.length; j++){
                    parseredData[i][j] = json.getString(jsonName[j]);
                }

            }
            //어떤식으로 분해하였는지 또 Log를 찍어 알아봅니다. 굳이 안 넣으셔도 됩니다.
            for(int i=0;i<parseredData.length;i++) {
                Log.i("JSON을 분석한 데이터"+i+":",parseredData[i][0]);
                Log.i("JSON을 분석한 데이터"+i+":",parseredData[i][1]);
                Log.i("JSON을 분석한 데이터"+i+":",parseredData[i][2]);
            }

            return parseredData; //잘 파싱된 데이터를 넘깁니다.
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");

        if(requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(LoginActivity.this, "로그인을 완료했습니다!", Toast.LENGTH_SHORT).show();
            etEmail.setText(data.getStringExtra("email"));
        }
    }

    private boolean loginValidation(String id, String password) {
        if(pref.getString("id","").equals(id) && pref.getString("pw","").equals(password)) {
            // login success
            return true;
        } else if (pref.getString("id","").equals(null)){
            // sign in first
            Toast.makeText(LoginActivity.this, "Please Sign in first", Toast.LENGTH_LONG).show();
            return false;
        } else {
            // login failed
            return false;
        }
    }

//    autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if(isChecked) {
//                loginChecked = true;
//            } else {
//                // if unChecked, removeAll
//                loginChecked = false;
//                editor.clear();
//                editor.commit();
//            }
//        }
//    });

*/

