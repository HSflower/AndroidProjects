//made by BBOL
//

package com.example.union_tab;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;


//구글 계정 로그인 다이얼로그
public class Account_dialog extends Dialog implements OnTouchListener{

	private EditText Account, password;
	private Button login, cancel;
	private String _Account = "";


	public Account_dialog(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_set);
		Account = (EditText) findViewById(R.id.Account);
		password = (EditText) findViewById(R.id.Password);
		login = (Button) findViewById(R.id.OK);
		cancel = (Button) findViewById(R.id.Cancel);
		
		login.setOnTouchListener(this);
		cancel.setOnTouchListener(this);

		
	}

	public String ret_Account()
	{
		return _Account;

	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		if(v == login)
		{
			_Account = Account.getText().toString();	
			dismiss();
		}
		
		else if (v == cancel)
		{
			dismiss();
		}
		return false;
	}
	
	
}
