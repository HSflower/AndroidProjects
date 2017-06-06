package com.example.whererhyu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Rest_Info_Fragment extends Fragment implements OnClickListener{
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	View rootView;
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	String jsonResult, jsonResult2;
	String rest_id;
	
	Button btn_call;
	
	public static Rest_Info_Fragment newInstance(int sectionNumber) {
		Rest_Info_Fragment fragment = new Rest_Info_Fragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public Rest_Info_Fragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.rest_info_fragment, container, false);
		rest_id = Rest_Detail_Activity.rest_id;
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager1);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		new Select().execute("http://hax4.woobi.co.kr/rest_info.php");
		btn_call = (Button)rootView.findViewById(R.id.rest_info_phone_call_btn);
		btn_call.setOnClickListener(this);
		return rootView;
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position) {
			case 0 :
				return Rest_Info_Image_Fragment_1.newInstance(position+1);
			case 1 :
				return Rest_Info_Image_Fragment_2.newInstance(position+1);
			case 2 :
				return Rest_Info_Image_Fragment_3.newInstance(position+1);
			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "Image1";//getString("Info").toUpperCase(l);
			case 1:
				return "Image2";//getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return "Image3";//getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", "yourAwesomeFragmentsTab");
		super.onSaveInstanceState(outState);
	}
	
	class Select extends AsyncTask<String, Void, String> {
		private StringBuilder inputStreamToString(InputStream is)
				throws IOException {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					"EUC-KR"));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getActivity().getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

		public void ListDrwaer() {
			try {
				JSONObject jsonResponse = new JSONObject(jsonResult2);
				JSONArray jsonMainNode = jsonResponse.optJSONArray("rest_fac");
				if (jsonMainNode != null) {
					for (int i = 0; i < jsonMainNode.length(); i++) {
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
							String fac_id = jsonChildNode.optString("fac_id");
							String fac_name = jsonChildNode.optString("category");
							String open = jsonChildNode.optString("open");
							String close = jsonChildNode.optString("close");
							LinearLayout fac_ll = (LinearLayout)rootView.findViewById(R.id.rest_info_fac_ll);
							ImageView fac_iv = new ImageView(getActivity());
							//int temp_id = getResources().getIdentifier(fac_id,"drawable",getActivity().getPackageName());
							//fac_iv.setImageResource(temp_id);
							//fac_ll.addView(fac_iv);
						}
					}
				jsonMainNode = jsonResponse.optJSONArray("phone");
				if (jsonMainNode != null) {
					for (int i = 0; i < jsonMainNode.length(); i++) {
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
						String phone = jsonChildNode.optString("phone");
						TextView phone_tv = new TextView(getActivity());
						phone_tv = (TextView)rootView.findViewById(R.id.rest_info_phone_tv);
						phone_tv.setText(phone);
					}
				}
				
			} catch (JSONException e) {
				Toast.makeText(getActivity().getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			Vector<NameValuePair> objValuePair = new Vector<NameValuePair>();
			objValuePair.add(new BasicNameValuePair("rest_id", rest_id));

			try {
				HttpEntity entity = new UrlEncodedFormEntity(objValuePair, HTTP.UTF_8);
				httppost.setEntity(entity);
				HttpResponse response = httpclient.execute(httppost);
				jsonResult2 = inputStreamToString(response.getEntity().getContent()).toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			ListDrwaer();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}