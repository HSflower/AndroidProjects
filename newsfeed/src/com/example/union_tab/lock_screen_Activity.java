package com.example.union_tab;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class lock_screen_Activity extends Activity{

	
	private DataListView lock_list;
	private SeekBar mUnlockBar;
	
	private IconTextListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		//무슨 일이 있던지 간에 setContentView는 항상 앞에!! -> 얘가 레이아웃 xml파일에서 있는 요소들을 가져와서 쓸 수 있게 해주는 역할을 한다.
		setContentView(R.layout.lockscreen);
		  mUnlockBar = (SeekBar) findViewById(R.id.seekBar1);
			// 일정간격을 맞추기위해
			mUnlockBar.setThumbOffset(10);
			mUnlockBar.setMax(100);
			mUnlockBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					if (seekBar.getProgress() >= 95) {
						seekBar.setProgress(100);
						seekBar.setEnabled(false);
						seekBar.setFocusable(false);
						Toast.makeText(getApplicationContext(), "Unlock On", Toast.LENGTH_SHORT).show();
						lock_screen_Activity.this.checkforseekber(mUnlockBar);
					} else {
						seekBar.setProgress(0);
					}
				}
			});

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
		
		lock_list = (DataListView)findViewById(R.id.lock_screen_list);
		lock_list.setAdapter(adapter);
		lock_list.setOnDataSelectionListener(new OnDataSelectionListener() {
			@Override
			public void onDataSelected(AdapterView parent, View v, int position, long id) {
				IconTextItem curItem = (IconTextItem) adapter.getItem(position);
				String[] curData = curItem.getData();

				Toast.makeText(getApplicationContext(), "Selected : " + curData[0], 2000).show();
			}

			
		});
	
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
	   
		   getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		      getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
		                  WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
	}
	
	
	
	public void checkforseekber(SeekBar seek)
	{
		if(!seek.isFocusable())
			this.finish();
	}
}
