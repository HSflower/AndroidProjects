package com.example.union_tab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Lock_screenBootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		 
		if(intent.getAction().equals(intent.ACTION_BOOT_COMPLETED));
		{
			Intent i = new Intent(context, lock_Screen_Service.class);
			context.startService(i);
		}
	}
}
