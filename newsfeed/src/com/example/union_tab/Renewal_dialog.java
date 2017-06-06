package com.example.union_tab;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//업데이트 주기 선택 다이얼로그

public class Renewal_dialog extends Dialog{

	private ListView Renew_list;
	private ArrayList<String> R_menu;
	private ArrayAdapter<String> R_adapter;
	private String What = "";
	
public Renewal_dialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.renewal_dialog);
	
	R_menu = new ArrayList<String>();
	R_menu.add("Per week");
	R_menu.add("Per day");
	R_menu.add("Per 1 hour");
	R_menu.add("immediately");
	
	R_adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, R_menu);
	
	Renew_list = (ListView)findViewById(R.id.renewal_List);
	Renew_list.setAdapter(R_adapter);
	Renew_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	Renew_list.setOnItemClickListener(new OnItemClickListener() {
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		What = R_menu.get(position).toString();
		dismiss();
	}
	}
	);
}

public String ret_Renewal()
{
	
	return What;
}

@Override
public boolean onTouchEvent(MotionEvent event) {
	// TODO Auto-generated method stub
	
	return super.onTouchEvent(event);
}
}

