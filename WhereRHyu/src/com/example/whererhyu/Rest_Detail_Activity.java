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

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Rest_Detail_Activity extends Activity implements OnClickListener, OnPageChangeListener{

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	static String rest_id;
	String phone;
	String jsonResult;
	
	TextView tv_rest_name;
	Button btn_refresh, btn_info, btn_notice, btn_customer;
	Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rest_detail_layout);
		
		mContext = this;
		
		Intent it = getIntent();
		rest_id = it.getStringExtra("id");
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(this);
		
		tv_rest_name = (TextView)findViewById(R.id.rest_detail_name_tv);
		
		btn_refresh = (Button)findViewById(R.id.rest_detail_refresh);
		btn_refresh.setOnClickListener(this);
		btn_info = (Button)findViewById(R.id.rest_detail_info_btn);
		btn_info.setOnClickListener(this);
		btn_notice = (Button)findViewById(R.id.rest_detail_notice_btn);
		btn_notice.setOnClickListener(this);
		btn_customer = (Button)findViewById(R.id.rest_detail_custom_btn);
		btn_customer.setOnClickListener(this);
		
		new Select().execute("http://hax4.woobi.co.kr/rest_detail.php");
		
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
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

		public void ListDrwaer() {
			try {
				JSONObject jsonResponse = new JSONObject(jsonResult);
				JSONArray jsonMainNode = jsonResponse.optJSONArray("rest_detail");
				if (jsonMainNode != null) {
					for (int i = 0; i < jsonMainNode.length(); i++) {
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
						String name = jsonChildNode.optString("name");
						tv_rest_name.setText(name);
					}
				}
				jsonMainNode = jsonResponse.optJSONArray("rest_avg_star");
				if (jsonMainNode != null) {
					for (int i = 0; i < jsonMainNode.length(); i++) {
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
						String name = jsonChildNode.optString("avg_star");
					}
				}
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
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
				jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
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
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position) {
			case 0 :
				return Rest_Info_Fragment.newInstance(position+1);
			case 1 :
				return Rest_Notice_Fragment.newInstance(position+1);
			case 2 :
				return Rest_Customer_Fragment.newInstance(position+1);
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
				return "Info";//getString("Info").toUpperCase(l);
			case 1:
				return "Notice";//getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return "Customer";//getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent it;
		switch(v.getId()) {
		case R.id.rest_detail_info_btn:
			mViewPager.setCurrentItem(0);
			btn_info.setBackgroundResource(R.drawable.rest_list_tab);
			btn_notice.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_customer.setBackgroundResource(R.drawable.rest_list_tab_2);
			break;
		case R.id.rest_detail_notice_btn:
			mViewPager.setCurrentItem(1);
			btn_info.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_notice.setBackgroundResource(R.drawable.rest_list_tab);
			btn_customer.setBackgroundResource(R.drawable.rest_list_tab_2);
			break;
		case R.id.rest_detail_custom_btn:
			mViewPager.setCurrentItem(2);
			btn_info.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_notice.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_customer.setBackgroundResource(R.drawable.rest_list_tab);
			break;
		case R.id.rest_detail_refresh:
			new Select().execute("http://hax4.woobi.co.kr/rest_detail.php");
			int page_num = mViewPager.getCurrentItem();
			mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			mViewPager.setCurrentItem(page_num);
			mViewPager.setOnPageChangeListener(this);
			break;
		
		}
		
	}
	
	@Override
	public void onBackPressed() {
		if(Rest_Notice_Fragment.linear_reply_view.isShown()) {
			Rest_Notice_Fragment.linear_reply_view.setVisibility(View.INVISIBLE);
			Rest_Notice_Fragment.listview_noti.setVisibility(View.VISIBLE);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		switch(arg0) {
		case 0:
			btn_info.setBackgroundResource(R.drawable.rest_list_tab);
			btn_notice.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_customer.setBackgroundResource(R.drawable.rest_list_tab_2);
			break;
		case 1:
			btn_info.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_notice.setBackgroundResource(R.drawable.rest_list_tab);
			btn_customer.setBackgroundResource(R.drawable.rest_list_tab_2);
			break;
		case 2:
			btn_info.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_notice.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_customer.setBackgroundResource(R.drawable.rest_list_tab);
			break;
		}
	}

}
