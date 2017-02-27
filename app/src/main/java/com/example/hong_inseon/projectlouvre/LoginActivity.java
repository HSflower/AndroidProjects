//http://cocomo.tistory.com/387
//http://swalloow.tistory.com/60
//jsp서버연동 : http://blog.naver.com/PostView.nhn?blogId=abcdtyy456&logNo=220597953881

package com.example.hong_inseon.projectlouvre;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegist; //sign up button
    private Button loginBtn; //Log in button
    CheckBox autoLogin;
    SharedPreferences pref = getSharedPreferences("Game", Activity.MODE_PRIVATE);
    SharedPreferences.Editor editor;
    Boolean loginChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.emailinput);
        etPassword = (EditText) findViewById(R.id.passwordInput);
        btnRegist = (Button) findViewById(R.id.signupBtn);
        autoLogin = (CheckBox) findViewById(R.id.autoLogin);
        loginBtn = (Button) findViewById(R.id.loginBtn);
    }

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

}
