package com.example.union_tab;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class Activity3 extends ListActivity {

	static String [][] Setting_menu = { 
			{"Account", "doooodle!"}
			,{"Refresh Rate", "per week"}
			, {"Version", "1.0"}
			,{"Lockscreen Notifier", "off"}
			};
	
	public static boolean lock_ok = false;
	
	 private SimpleAdapter sa;
	 
	 private ArrayList<HashMap<String,String>> list = 
			 new ArrayList<HashMap<String,String>>();
	 
	 private HashMap<String,String> item;
	 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
      
       
        for(int i=0;i<Setting_menu.length;i++){
        	 item = new HashMap<String,String>();
        	 item.put( "line1", Setting_menu[i][0]);
        	 item.put( "line2", Setting_menu[i][1]);
        	 list.add( item );
        }

        sa = new SimpleAdapter(this, list,
        		 android.R.layout.two_line_list_item ,
        		 new String[] { "line1","line2" },
        		 new int[] {android.R.id.text1, 
        		android.R.id.text2});
     setListAdapter(sa);
     Toast.makeText(this, "it's created!", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		if(position == 0)
		{
			
			final Account_dialog Account = new Account_dialog(this);
			Account.setTitle("Log In");
			Account.setOnCancelListener(new OnCancelListener() {
				
				@Override//Cancel �좎뜫�됭굢占폛ismiss占쎌뮋�숋옙釉앹삕藥뀐옙
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
				}
			});
			
			Account.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					Setting_menu[0][1] = Account.ret_Account();
					 list.clear();
					  for(int i=0;i<Setting_menu.length;i++)
					  {
				        	 item = new HashMap<String,String>();
				        	 item.put( "line1", Setting_menu[i][0]);
				        	 item.put( "line2", Setting_menu[i][1]);
				        	
				        	 list.add(item);
				        }

				}
					
					}
			);
			Account.show();	
	}
		if(position == 1)
		{
			final Renewal_dialog r = new Renewal_dialog(this);
			r.setTitle("Refresh Rate");
			r.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					Setting_menu[1][1] = r.ret_Renewal();
					 list.clear();
					  for(int i=0;i<Setting_menu.length;i++)
					  {
				        	 item = new HashMap<String,String>();
				        	 item.put( "line1", Setting_menu[i][0]);
				        	 item.put( "line2", Setting_menu[i][1]);
				        	
				        	 list.add(item);
				        }

					Toast.makeText(getApplicationContext(), "Dismiss", 2000).show();
				}
			});
			
			r.show();
		}
		
		if(position == 2)
		{
			Intent test_webview = new Intent(this, WebView_ForTest.class);
			startActivity(test_webview);
		}
		if(position == 3)
		{
			if(!lock_ok)
			{
			 Intent intent = new Intent(this, lock_Screen_Service.class);
		     startService(intent);
		     Setting_menu[3][1] = "on";
		     Toast.makeText(getApplicationContext(), "the lockscreen service has been started!", Toast.LENGTH_LONG).show();
		     lock_ok = true;
			}
			
			else
			{
				Intent intent = new Intent(this, lock_Screen_Service.class);
				stopService(intent);
				Setting_menu[3][1] = "off";
				 Toast.makeText(getApplicationContext(), "the lockscreen service has been stopped!", Toast.LENGTH_LONG).show();
				 lock_ok = false;
			}
			
			 list.clear();
			  for(int i=0;i<Setting_menu.length;i++)
			  {
		        	 item = new HashMap<String,String>();
		        	 item.put( "line1", Setting_menu[i][0]);
		        	 item.put( "line2", Setting_menu[i][1]);
		        	
		        	 list.add(item);
		        }
			 
			
		}
		sa.notifyDataSetChanged();
	}
}
