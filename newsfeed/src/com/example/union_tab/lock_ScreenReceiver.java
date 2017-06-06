package com.example.union_tab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class lock_ScreenReceiver extends BroadcastReceiver{

	private TelephonyManager telmanager = null;
	private boolean isPhoneIdle = true;
	private PhoneStateListener phoneListener
	 = new PhoneStateListener()
	{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			switch(state)
			{
			case TelephonyManager.CALL_STATE_IDLE:
				isPhoneIdle = true;
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				isPhoneIdle = false;
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				isPhoneIdle = false;
				break;
			}
		}
	};


	
	@Override
public void onReceive(Context context, Intent intent) {
	// TODO Auto-generated method stub

		
		
	if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
	{
		if(telmanager == null)
		{
			telmanager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			telmanager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
		}
		
		
		if(isPhoneIdle)
		{
		Intent i = new Intent(context, lock_screen_Activity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		}
		}
	}
}