//http://cocomo.tistory.com/387
//http://swalloow.tistory.com/60
//jsp서버연동 : http://blog.naver.com/PostView.nhn?blogId=abcdtyy456&logNo=220597953881

package com.example.hong_inseon.projectlouvre;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegist; //sign up button
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

        // if autoLogin checked, get input
        if (pref.getBoolean("autoLogin", false)) {
            etEmail.setText(pref.getString("id", ""));
            etPassword.setText(pref.getString("pw", ""));
            autoLogin.setChecked(true);
            // goto mainActivity

        } else {
            // if autoLogin unChecked
            String id = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            Boolean validation = loginValidation(id, password);

            if(validation) {
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                // save id, password to Database

                if(loginChecked) {
                    // if autoLogin Checked, save values
                    editor.putString("id", id);
                    editor.putString("pw", password);
                    editor.putBoolean("autoLogin", true);
                    editor.commit();
                }
                // goto mainActivity

            } else {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                // goto LoginActivity1
            }
        }

        //회원가입창으로 이동
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // 동시에 사용 가능
                // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intent.putExtra("USERNAME_KEY","jizard"); //키 - 보낼 값(밸류)
                intent.putExtra("BIRTHDAY_KEY",119);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                startActivityForResult(intent, 1000);
                /* http://jizard.tistory.com/10
                // 받는 액티비티에서는
        Intent intent = getIntent(); //이 액티비티를 부른 인텐트를 받는다.
        String username = intent.getStringExtra("USERNAME_KEY"); //"jizard"문자 받아옴
        int birthday = intent.getIntExtra("BIRTHDAY_KEY",0); //119 받아옴
                 */
            }
        });
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
        String sMessage = etEmail.getText().toString();
        String result = SendByHttp(sMessage);
        String[][] parsedData = jsonParserList(result);
        //받아온 형태가 json이므로 파싱합니다. jsonParserList함수는 받아온 데이터를 파싱하는 함수입니다
        if(parsedData[0][0].equals("succed"))
        {
        //이 부분은 제가 아이디랑 비밀번호를 get방식으로 보냈죠? 그 데이터가 웹으로 날아가서 db 내용들과 비교한 뒤,
        //올바른 경우에는 웹에서 json형태로 첫번째 배열에 succed를 넣었습니다.아닌경우에는 failed를 넣었고요.
        //그걸 비교해서 succed일 경우에는 로그인 성공 매세지를 띄우고(메인엑티비티일 경우 그냥 this로 쓰세요~),
        //메인 클래스로 인텐트 하는 부분입니다.
            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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

    private String SendByHttp(String msg) {
        if (msg == null) {
            msg = "";
        }
/*
        String URL = "http://자신의 아이피:8080/프로젝트명/~~~~.jsp"; //자신의 웹서버 주소를 저장합니다.
        DefaultHttpClient client = new DefaultHttpClient();//HttpClient 통신을 합니다.
//Http 라이브러리가 필요합니다.
        try {
            HttpPost post = new HttpPost(URL + "?id=" + edit_id.getText().toString()+"&pw="+edit_pw.getText().toString());
//웹서버로 데이터를 전송합니다. 요번에 경우에는 get방식으로 데이터를 전송하였습니다.
//간단히 설명하면 주소?데이터명=데이터내용&데이터명=데이터내용   이런식입니다.
            HttpResponse response = client.execute(post);
//데이터를 보내고 바로 데이터 응답을 받습니다.
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
//받아온 데이터를 buffer에 넣습니다.
            String line = null;
            String result = "";

            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
//buffer를 읽어와서 result에 넣습니다.
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            client.getConnectionManager().shutdown();
//예외처리입니다.
            return "";
        }
        */
        return "";
    }

    //받아온 데이터를 파싱하는 부분입니다.
    public String[][] jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
//받아온 데이터를 확인합니다. 이 부분을 확인하고 싶으시면, 안드로이드 하단에 쭉쭉 뭐 뜨는거 보이시면 그거 Log들인데 거기게 흔적을 남기는 겁니다. info에서 찾아보시면 찾으실 수 있습니다.
        try{
//아까말한 {"List":[{"data1":"sfasf".""data2:"sdfsdf"}]} 이형태를 분해하는 과정입니다.
//저도 잘 몰라요..
//아래 어떤 대단한 분에 코딩을 참고하였습니다. 이 사이트에서 자세한 내용을 확인해보세요.^^
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
            for(int i=0;i<parseredData.length;i++)
            {
                Log.i("JSON을 분석한 데이터"+i+":",parseredData[i][0]);
                Log.i("JSON을 분석한 데이터"+i+":",parseredData[i][1]);
                Log.i("JSON을 분석한 데이터"+i+":",parseredData[i][2]);
            }


            return parseredData;
//잘 파싱된 데이터를 넘깁니다.
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

}
