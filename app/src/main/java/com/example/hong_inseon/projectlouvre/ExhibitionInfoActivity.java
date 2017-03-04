package com.example.hong_inseon.projectlouvre;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
=======
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
>>>>>>> pmj
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


<<<<<<< HEAD
public class ExhibitionInfoActivity extends AppCompatActivity implements OnClickListener{
=======
public class ExhibitionInfoActivity extends Activity implements
        OnClickListener,NavigationView.OnNavigationItemSelectedListener {
>>>>>>> pmj

    final Context context = this;
    private Button btnOptAlert;

    public static Intent aa;
    public static Intent bb;
    public static Intent cc;
    public static Intent dd;
    public static int men = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition_info_drawer);

        aa = new Intent(this, Cart.class);
        bb = new Intent(this, Profile.class);
        cc = new Intent(this, LikeExhibition.class);
        dd = new Intent(this, LikeMuseum.class);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }//뒤로가기

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.some, menu);
        return true;
    }//검색창

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, OptionP.class);
        startActivity(i);

        return super.onOptionsItemSelected(item);
    }//검색창

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cartid) {
            startActivityForResult(aa,0);
        } else if (id == R.id.profileid) {
            startActivityForResult(bb,1);
        } else if (id == R.id.heartid) {
            startActivityForResult(cc,2);
        } else if (id == R.id.templeid) {
            startActivityForResult(dd,3);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }//드로워 메뉴 선택

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0){

        } else if(resultCode == 1) {
            men = data.getIntExtra("value1", -1);
            if (men == 0) {
                startActivityForResult(aa, 0);
            } else if (men == 1) {
                startActivityForResult(bb, 1);
            } else if (men == 2) {
                startActivityForResult(cc, 2);
            } else if (men == 3) {
                startActivityForResult(dd, 3);
            }
        }
    }//메뉴에서 다른메뉴로 넘어갈때 쓰이는 함수
}