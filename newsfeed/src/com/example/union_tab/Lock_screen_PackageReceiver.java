package com.example.union_tab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Lock_screen_PackageReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	String action = intent.getAction();
	
	if(action.equals(intent.ACTION_PACKAGE_ADDED))
	{
		
	}
	else if(action.equals(intent.ACTION_PACKAGE_REMOVED))
			{
		
			}
	else if (action.equals(intent.ACTION_PACKAGE_REPLACED))
	{
		Intent i = new Intent(context, lock_Screen_Service.class);
		context.startService(i);
	}
	}
}
