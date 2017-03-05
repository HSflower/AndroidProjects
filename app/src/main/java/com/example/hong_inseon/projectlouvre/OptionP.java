package com.example.hong_inseon.projectlouvre;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class OptionP extends AppCompatActivity {
    ListView list, list2;
    ListViewAdapterMuseum adapter;
    ListViewAdapterExhibition adapter2;
    EditText editsearch;
    TextView t,t1,t2;
    int[] Image, rating, Image2;
    String[] name4, name2, name1, name3, name5;
    ArrayList<Museum> arraylist = new ArrayList<Museum>();
    ArrayList<Exhibition> arraylist2 = new ArrayList<Exhibition>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_p);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        //액션바에서 가운데 맞춤을 해주는 것
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yourimage));
        //위에는 이미지를 넣고 싶을때 사용

        name1 = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};
        rating = new int[] { 4, 3, 5, 2, 4, 3, 4, 5, 1, 5};
        name2 = new String[] { "경기도 부천시 원미구", "서울특별시 강남구", "인천광역시 남동구 백범로 124번길",
                "강원도 홍천시", "인천광역시 연수구 옥련동", "부산광역시 어딘가", "미국 Los Angelous 인지 어딘지 모름"
                , "우주 안드로메다","이세상 어딘가에 있을거라고 믿는곳", "도서관 4층 일반자료실 노트북코너"};
        Image = new int[] {R.drawable.no,R.drawable.cart,R.drawable.heart,R.drawable.louvre,R.drawable.profile,
                R.drawable.mypage,R.drawable.temple,R.drawable.search,R.drawable.cart,R.drawable.profile};

        name3 = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};
        name4 = new String[] { "Beijing", "New Delhi", "Washington D.C.",
                "Jakarta", "Brazilia", "Islamabad", "Abuja", "Dacca",
                "Moskva", "Tokyo"};
        name5 = new String[] { "2017.02.28~2017.07.21", "2017.02.27~2017.07.21", "2017.02.28~2017.07.21",
                "2017.02.26~2017.07.21", "2017.02.25~2017.07.21", "2017.02.24~2017.07.21", "2017.02.23~2017.07.21"
                , "2017.02.22~2017.07.21","2017.02.21~2017.07.21", "2017.02.20~2017.07.21"};
        Image2 = new int[] {R.drawable.no,R.drawable.cart,R.drawable.heart,R.drawable.louvre,R.drawable.profile,
                R.drawable.mypage,R.drawable.temple,R.drawable.search,R.drawable.cart,R.drawable.profile};
        //관련 자료들 저장 - DB에서 쓸 자료들의 예시

        list = (ListView) findViewById(R.id.listview);          //박물관 검색 결과를 띄울 리스트
        list2 = (ListView)findViewById(R.id.listview2);         //전시회 검색 결과를 띄울 리스트
        t = (TextView)findViewById(R.id.Result);                //찾은 검색 정보를 띄울 텍스트
        t1 = (TextView)findViewById(R.id.textMuseum);           //검색시 "박물관"을 띄울 텍스트, edit text가 변할시 사라진다
        t2 = (TextView)findViewById(R.id.textExhibition);      //검색시 "전시회"를 띄울 텍스트, 위와 같다

        for (int i = 0; i < name1.length; i++)
        {
            Museum wp = new Museum(name1[i], rating[i], name2[i], Image[i]);
            arraylist.add(wp);
        }
        //박물관 검색 리스트의 뷰홀더에 쓰일 클래스의 생성자를 자료들로 설정

        for (int i = 0; i < name2.length; i++) {
            Exhibition wp2 = new Exhibition(name3[i], name4[i], name5[i], Image2[i]);
            arraylist2.add(wp2);
        }
        //전시회 검색 리스트의 뷰홀더에 쓰일 클래스의 생성자를 자료들로 설정

        adapter = new ListViewAdapterMuseum(this, arraylist);
        adapter2 = new ListViewAdapterExhibition(this, arraylist2);

        list.setAdapter(adapter);           //어댑터 설정(박물관)
        list2.setAdapter(adapter2);         //어댑터 설정(전시회)

        editsearch = (EditText) findViewById(R.id.search);          //검색 창
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
// TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                t1.setVisibility(INVISIBLE);
                t2.setVisibility(INVISIBLE);
                list.setVisibility(INVISIBLE);
                list2.setVisibility(INVISIBLE);
                adapter.filter(text);
                adapter2.filter(text);
            }
            //만약 텍스트에 변화가 있으면 위와 같이 설정한다.

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
// TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
// TODO Auto-generated method stub
            }
        });
    }

    public void find(View v) {
        t.setText("찾은 검색 : 박물관 " + adapter.getCount() + "건, 전시회 " +adapter2.getCount() + "건");
        t1.setVisibility(VISIBLE);
        t2.setVisibility(VISIBLE);
        list.setVisibility(VISIBLE);
        list2.setVisibility(VISIBLE);
        Utility.setListViewHeightBasedOnChildren(list);
        Utility.setListViewHeightBasedOnChildren(list2);
    }
    //검색을 클릭시 설정할 메소드들

    public static class Utility {
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }
    //리스트의 크기를 구해 전체 크기를를 맞춰주는 함수
}
