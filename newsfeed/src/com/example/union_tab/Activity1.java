package com.example.union_tab;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

public class Activity1 extends Activity {

	/**
	 * 리스트뷰 객체
	 */
	DataListView list;
	
	/**
	 * 어댑터 객체
	 */
	IconTextListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
        // 리스트뷰 객체 생성
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        list = new DataListView(this);

        // 어댑터 객체 생성
        adapter = new IconTextListAdapter(this);

		adapter.addItem(new IconTextItem("추억의 테트리스", "30,000 다운로드", "900 원"));
		adapter.addItem(new IconTextItem("고스톱 - 강호동 버전", "26,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem("친구찾기 (Friends Seeker)", "300,000 다운로드", "900 원"));
		adapter.addItem(new IconTextItem("강좌 검색", "120,000 다운로드", "900 원"));
		adapter.addItem(new IconTextItem("지하철 노선도 - 서울", "4,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem("지하철 노선도 - 도쿄", "6,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem("지하철 노선도 - LA", "8,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem("지하철 노선도 - 워싱턴", "7,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem("지하철 노선도 - 파리", "9,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem("지하철 노선도 - 베를린", "38,000 다운로드", "1500 원"));

		// 리스트뷰에 어댑터 설정
		list.setAdapter(adapter);

		// 새로 정의한 리스너로 객체를 만들어 설정
		list.setOnDataSelectionListener(new OnDataSelectionListener() {
			public void onDataSelected(AdapterView parent, View v, int position, long id) {
				IconTextItem curItem = (IconTextItem) adapter.getItem(position);
				String[] curData = curItem.getData();

				Toast.makeText(getApplicationContext(), "Selected : " + curData[0], 2000).show();
			}
		});


        // 화면을 리스트뷰 객체로 채움
        setContentView(list, params);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
