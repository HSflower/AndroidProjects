package com.example.union_tab;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		    TabHost tabHost = getTabHost();  // The activity TabHost
		    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
		    // Initialize a TabSpec for each tab and add it to the TabHost
		    spec = tabHost.newTabSpec("lists").setIndicator("lists").setContent(new Intent(this, Activity1.class));
		    tabHost.addTab(spec);
		    spec = tabHost.newTabSpec("favorites").setIndicator("favorites").setContent(new Intent(this, Activity2.class));
		    tabHost.addTab(spec);
		    spec = tabHost.newTabSpec("settings").setIndicator("Settings").setContent(new Intent(this, Activity3.class));
		    tabHost.addTab(spec);
		    tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

/*
 *
 * package com.example.union_tab;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 Resources res = getResources(); // Resource object to get Drawables
		    TabHost tabHost = getTabHost();  // The activity TabHost
		    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
		    Intent intent;         // Reusable Intent for each tab
		    // Create an Intent to launch an Activity for the tab (to be reused)
		    intent = new Intent().setClass(this, Activity1.class);
		    // Initialize a TabSpec for each tab and add it to the TabHost
		    spec = tabHost.newTabSpec("lists").setIndicator("lists").setContent(new Intent(this, Activity1.class));
		    tabHost.addTab(spec);
		    // Do the same for the other tab
		    intent = new Intent().setClass(this, Activity2.class);
		    spec = tabHost.newTabSpec("favorites").setIndicator("favorites").setContent(new Intent(this, Activity2.class));
		    tabHost.addTab(spec);
		    intent = new Intent().setClass(this, Activity3.class);
		    spec = tabHost.newTabSpec("settings").setIndicator("Settings").setContent(new Intent(this, Activity3.class));
		    tabHost.addTab(spec);
		    tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

 * 
 */
