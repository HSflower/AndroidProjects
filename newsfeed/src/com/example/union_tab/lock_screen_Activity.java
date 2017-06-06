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
		//���� ���� �ִ��� ���� setContentView�� �׻� �տ�!! -> �갡 ���̾ƿ� xml���Ͽ��� �ִ� ��ҵ��� �����ͼ� �� �� �ְ� ���ִ� ������ �Ѵ�.
		setContentView(R.layout.lockscreen);
		  mUnlockBar = (SeekBar) findViewById(R.id.seekBar1);
			// ���������� ���߱�����
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
		adapter.addItem(new IconTextItem("�߾��� ��Ʈ����", "30,000 �ٿ�ε�", "900 ��"));
		adapter.addItem(new IconTextItem("���� - ��ȣ�� ����", "26,000 �ٿ�ε�", "1500 ��"));
		adapter.addItem(new IconTextItem("ģ��ã�� (Friends Seeker)", "300,000 �ٿ�ε�", "900 ��"));
		adapter.addItem(new IconTextItem("���� �˻�", "120,000 �ٿ�ε�", "900 ��"));
		adapter.addItem(new IconTextItem("����ö �뼱�� - ����", "4,000 �ٿ�ε�", "1500 ��"));
		adapter.addItem(new IconTextItem("����ö �뼱�� - ����", "6,000 �ٿ�ε�", "1500 ��"));
		adapter.addItem(new IconTextItem("����ö �뼱�� - LA", "8,000 �ٿ�ε�", "1500 ��"));
		adapter.addItem(new IconTextItem("����ö �뼱�� - ������", "7,000 �ٿ�ε�", "1500 ��"));
		adapter.addItem(new IconTextItem("����ö �뼱�� - �ĸ�", "9,000 �ٿ�ε�", "1500 ��"));
		adapter.addItem(new IconTextItem("����ö �뼱�� - ������", "38,000 �ٿ�ε�", "1500 ��"));
		
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
