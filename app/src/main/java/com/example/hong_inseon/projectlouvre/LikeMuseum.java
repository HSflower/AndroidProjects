package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class LikeMuseum extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView list;
    ListViewAdapterMuseum listh;
    String[] name1, name2;
    int[] Image, rating;
    ArrayList<Museum> arraylist = new ArrayList<Museum>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view3);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);

        name1 = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};
        rating = new int[] { 4, 3, 5, 2, 4, 3, 4, 5, 1, 5};
        name2 = new String[] { "경기도 부천시 원미구", "서울특별시 강남구", "인천광역시 남동구 백범로 124번길",
                "강원도 홍천시", "인천광역시 연수구 옥련동", "부산광역시 어딘가", "미국 Los Angelous 인지 어딘지 모름"
                , "우주 안드로메다","이세상 어딘가에 있을거라고 믿는곳", "도서관 4층 일반자료실 노트북코너"};
        Image = new int[] {R.drawable.no,R.drawable.cart,R.drawable.heart,R.drawable.louvre,R.drawable.profile,
                R.drawable.mypage,R.drawable.temple,R.drawable.search,R.drawable.cart,R.drawable.profile};

        list = (ListView)findViewById(R.id.listViewTemple);

        for (int i = 0; i < name1.length; i++)
        {
            Museum hs = new Museum(name1[i], rating[i] ,name2[i], Image[i]);
            arraylist.add(hs);
        }
        //리스트배열을 정리

        listh = new ListViewAdapterMuseum(this, arraylist);
        list.setAdapter(listh);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cartid) {
            Intent i = new Intent(this, Cart.class);
            startActivity(i);
        } else if (id == R.id.profileid) {
            Intent i = new Intent(this, Profile.class);
            startActivity(i);
        } else if (id == R.id.heartid) {
            Intent i = new Intent(this, LikeExhibition.class);
            startActivity(i);
        } else if (id == R.id.templeid) {
            Intent i = new Intent(this, LikeMuseum.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
