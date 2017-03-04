package com.example.hong_inseon.projectlouvre;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


public class ExhibitionInfoActivity extends AppCompatActivity implements OnClickListener{

    final Context context = this;
    private Button btnOptAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition_info_drawer);

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

        final CharSequence [] items = {"가이드", "도록", "가이드+도록"};
        final int [] selectedIndex = {0};

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setPositiveButton("구매", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //프로그램을 종료한다
                Toast.makeText(getApplicationContext(), items[selectedIndex[0]] + " 구매했습니다.",
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.setSingleChoiceItems(items,
                -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        selectedIndex[0] = which;

                    }
                });

        dialog.show();

        /*
        dialog.setItems(items, new DialogInterface.OnClickListener(){
            //리스트 선택시 이벤트

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), items[which]+" 선택했습니다", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();



        final CharSequence[] items = {"가이드", "도록", "가이드+도록"};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);


        //alertDialogBuilder.setTitle("옵션 선택 목록 대화상자"); //제목 셋팅



        //다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        //alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        LayoutParams params = alertDialogBuilder.getContext().
                //getWindow().getAttributes();
        params.x=100;
        params.y=200;
        alertDialog.getWindow().setAttributes(params);


        //다이얼로그 보여주기
        alertDialog.show();

        */
    }
}