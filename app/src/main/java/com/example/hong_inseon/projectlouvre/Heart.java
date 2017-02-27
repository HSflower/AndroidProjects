package com.example.hong_inseon.projectlouvre;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Heart extends AppCompatActivity {

    ListView list;
    ListViewAdapterH listh;
    String[] name1, name2, name3;
    int[] Image;
    ArrayList<HeartS> arraylist = new ArrayList<HeartS>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);

        name1 = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};

        name2 = new String[] { "Beijing", "New Delhi", "Washington D.C.",
                "Jakarta", "Brazilia", "Islamabad", "Abuja", "Dacca",
                "Moskva", "Tokyo"};
        name3 = new String[] { "2017.02.28~2017.07.21", "2017.02.27~2017.07.21", "2017.02.28~2017.07.21",
                "2017.02.26~2017.07.21", "2017.02.25~2017.07.21", "2017.02.24~2017.07.21", "2017.02.23~2017.07.21"
                , "2017.02.22~2017.07.21","2017.02.21~2017.07.21", "2017.02.20~2017.07.21"};
        Image = new int[] {R.drawable.no,R.drawable.cart,R.drawable.heart,R.drawable.louvre,R.drawable.profile,
                            R.drawable.mypage,R.drawable.temple,R.drawable.search,R.drawable.cart,R.drawable.profile};
        //리스트에 들어갈 자료들 정리

        list = (ListView)findViewById(R.id.heartlist);

        for (int i = 0; i < name1.length; i++)
        {
            HeartS hs = new HeartS(name1[i],name2[i],name3[i], Image[i]);
            arraylist.add(hs);
        }
        //리스트배열을 정리

        listh = new ListViewAdapterH(this, arraylist);
        list.setAdapter(listh);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.some, menu);
        return true;
    }
}
