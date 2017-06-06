package com.example.union_tab;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class lock_Screen_Service extends Service {

	
	private lock_ScreenReceiver mReceiver;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	
		mReceiver = new lock_ScreenReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(mReceiver, filter);
		Toast.makeText(getApplicationContext(), "i'm service!", Toast.LENGTH_LONG).show();
	
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
	
		if(intent != null)
		{
			if(intent.getAction() == null)
			{
				if(mReceiver == null)
				{
					IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
					registerReceiver(mReceiver, filter);
				}
			}
		}
		
		Notification t = new Notification();
		startForeground(1, t);
		return START_REDELIVER_INTENT;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if(mReceiver != null)
		{
			unregisterReceiver(mReceiver);
		}
	}
}
