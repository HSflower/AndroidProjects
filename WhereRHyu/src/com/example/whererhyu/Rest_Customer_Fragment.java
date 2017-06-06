package com.example.whererhyu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;

public class Rest_Customer_Fragment extends Fragment{
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	public static Rest_Customer_Fragment newInstance(int sectionNumber) {
		Rest_Customer_Fragment fragment = new Rest_Customer_Fragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public Rest_Customer_Fragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.rest_customer_fragment, container, false);
		return rootView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", "yourAwesomeFragmentsTab");
		super.onSaveInstanceState(outState);
	}

}