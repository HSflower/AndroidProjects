package com.example.whererhyu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Rest_Notice_Fragment extends Fragment {
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	Rest_Notice_Adapter adapter;
	static Rest_Notice_Reply_Adapter r_adapter;
	ArrayList<Rest_Notice_Item> mItems;
	static ArrayList<Rest_Notice_Reply_Item> rItems;
	static ListView listview_noti;
	static LinearLayout linear_reply_view;
	static ListView listview_reply;
	String jsonResult;
	String rest_id;
	static int pnum;
	int offset = 0;
	
	public static Rest_Notice_Fragment newInstance(int sectionNumber) {
		Rest_Notice_Fragment fragment = new Rest_Notice_Fragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public Rest_Notice_Fragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.rest_notice_fragment, container, false);
		rest_id = Rest_Detail_Activity.rest_id;
		listview_noti = (ListView)rootView.findViewById(R.id.rest_notice_list);
		
		linear_reply_view = (LinearLayout)rootView.findViewById(R.id.rest_notice_reply_view);
		listview_reply = (ListView)rootView.findViewById(R.id.rest_notice_reply_list);
		
		
		mItems = new ArrayList<Rest_Notice_Item>();
		mItems.clear();
		adapter = new Rest_Notice_Adapter(getActivity(), mItems);
		listview_noti.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		rItems = new ArrayList<Rest_Notice_Reply_Item>();
		rItems.clear();
		r_adapter = new Rest_Notice_Reply_Adapter(getActivity(), rItems);
		listview_reply.setAdapter(r_adapter);
		r_adapter.notifyDataSetChanged();
		
		new Select().execute("http://hax4.woobi.co.kr/notification.php");
		return rootView;
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
				JSONObject jsonResponse = new JSONObject(jsonResult);
				JSONArray jsonMainNode = jsonResponse.optJSONArray("notification");
				if (jsonMainNode != null) {
					for (int i = 0; i < jsonMainNode.length(); i++) {
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
						int pnum = jsonChildNode.optInt("pnum");
						String title = jsonChildNode.optString("title");
						String temp_time = jsonChildNode.optString("time");
						String time[] = temp_time.split(":");
						String cont = jsonChildNode.optString("cont");
						String photo_s = jsonChildNode.optString("photo");
						if(photo_s.length() == 0) {
							mItems.add(new Rest_Notice_Item(pnum, title, time[0]+":"+time[1], cont));
						} else {
							byte[] photo = Base64.decode(photo_s, Base64.DEFAULT);
							mItems.add(new Rest_Notice_Item(pnum, title, time[0]+":"+time[1], cont, photo));
						}
						adapter.notifyDataSetChanged();
						offset++;
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
			objValuePair.add(new BasicNameValuePair("offset", ""+offset));

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
	
}