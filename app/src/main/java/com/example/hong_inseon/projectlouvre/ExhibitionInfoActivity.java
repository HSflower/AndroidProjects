package com.example.hong_inseon.projectlouvre;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


public class ExhibitionInfoActivity extends Activity implements
        OnClickListener{

    final Context context = this;
    private Button btnOptAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition_info);

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://ec2-35-161-181-60.us-west-2.compute.amazonaws.com:8080/ProjectLOUVRE/getExhiTab.jsp");

        //if(VERSION.SDK_INT >= 19) {
        //    webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //}

        //옵션 상자
        btnOptAlert = (Button) findViewById(R.id.buttonBuyOpt);
        //클릭이벤트
        btnOptAlert.setOnClickListener(this);
    }

    public void onClick(View v) {
        final CharSequence[] items = {"가이드", "도록", "가이드+도록"};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        //제목 셋팅
        alertDialogBuilder.setTitle("옵션 선택 목록 대화상자");
        alertDialogBuilder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        //프로그램을 종료한다
                        Toast.makeText(getApplicationContext(), items[id]+"선택했습니다.",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

        //다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        //다이얼로그 보여주기
        alertDialog.show();
    }
}