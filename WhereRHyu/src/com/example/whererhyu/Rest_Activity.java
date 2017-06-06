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

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

public class Rest_Activity extends NMapActivity implements
		OnMapStateChangeListener, OnClickListener, OnItemClickListener {
	LinearLayout MapContainer;
	NMapView nMap;
	NMapController nMapControler;
	private NMapViewerResourceProvider mMapViewerResourceProvider;
	private NMapOverlayManager mOverlayManager;
	NMapPOIdata poiData;
	int markerId;

	public static final String API_KEY = "8dded4fa55857775e913549a5ec8e488";

	int flag = 2;

	private String jsonResult;

	ListView rest_listview;
	ArrayList<Rest_Item> mItems;
	ArrayList<Rest_Item> mItems_u;
	ArrayList<Rest_Item> mItems_d;
	Rest_Adapter adapter;

	Button btn_up, btn_down, btn_all, btn_to_detail, btn_refresh;

	String rest_id = "";

	Context mContext;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rest_layout);
		
		mContext = this;
		btn_all = (Button) findViewById(R.id.rest_all_btn);
		btn_up = (Button) findViewById(R.id.rest_upward_btn);
		btn_down = (Button) findViewById(R.id.rest_downward_btn);
		btn_to_detail = (Button) findViewById(R.id.rest_to_detail_btn);
		btn_refresh = (Button) findViewById(R.id.rest_refresh_bth);

		btn_all.setOnClickListener(this);
		btn_up.setOnClickListener(this);
		btn_down.setOnClickListener(this);
		btn_to_detail.setOnClickListener(this);
		btn_refresh.setOnClickListener(this);

		MapContainer = (LinearLayout) findViewById(R.id.rest_map_linear);
		rest_listview = (ListView) findViewById(R.id.rest_list_listview);
		rest_listview.setOnItemClickListener(this);

		mItems = new ArrayList<Rest_Item>();
		mItems.clear();
		mItems_u = new ArrayList<Rest_Item>();
		mItems_u.clear();
		mItems_d = new ArrayList<Rest_Item>();
		mItems_d.clear();

		adapter = new Rest_Adapter(this, mItems);
		rest_listview.setAdapter(adapter);

		nMap = new NMapView(this);
		nMapControler = nMap.getMapController();
		nMap.setApiKey(API_KEY);
		MapContainer.addView(nMap);
		nMap.setBuiltInAppControl(false);
		nMap.setBuiltInZoomControls(false, null);
		nMap.setOnMapStateChangeListener(this);
		nMapControler.setMapViewTrafficMode(true);
		
		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
		mOverlayManager = new NMapOverlayManager(this, nMap, mMapViewerResourceProvider);
		markerId = NMapPOIflagType.PIN;
		
		poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
		poiData.beginPOIdata(1);
		new Select().execute("http://hax4.woobi.co.kr/restarea.php");

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
				JSONArray jsonMainNode = jsonResponse.optJSONArray("restarea");
				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
					String id = jsonChildNode.optString("rest_id");
					String name = jsonChildNode.optString("rest_name");
					float latitude = (float) jsonChildNode
							.optDouble("r_latitude");
					float longitude = (float) jsonChildNode
							.optDouble("r_longitude");
					float star_point = (float) jsonChildNode
							.optDouble("estimation");
					int updown = jsonChildNode.optInt("r_updownward");
					mItems.add(new Rest_Item(id, name, latitude, longitude,
							star_point, updown));
					if (updown == 0 || updown == 2) {
						mItems_d.add(new Rest_Item(id, name, latitude,
								longitude, star_point, updown));
					}
					if (updown == 1 || updown == 2) {
						mItems_u.add(new Rest_Item(id, name, latitude,
								longitude, star_point, updown));
					}
				}
				adapter.notifyDataSetChanged();
				poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
				poiData.beginPOIdata(1);
				poiData.addPOIitem(mItems.get(0).longitude, mItems.get(0).latitude, mItems.get(0).name, markerId, 0);
				poiData.endPOIdata();
				NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
				poiDataOverlay.showAllPOIdata(0);
				nMapControler.setMapCenter(new NGeoPoint(mItems.get(0).longitude, mItems.get(0).latitude), 13);
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
			objValuePair.add(new BasicNameValuePair("exp_id", "0010"));

			try {
				HttpEntity entity = new UrlEncodedFormEntity(objValuePair,
						HTTP.UTF_8);
				httppost.setEntity(entity);
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
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
	public void onAnimationStateChange(NMapView arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapCenterChange(NMapView arg0, NGeoPoint arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapCenterChangeFine(NMapView arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapInitHandler(NMapView arg0, NMapError arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onZoomLevelChange(NMapView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rest_all_btn:
			adapter = new Rest_Adapter(this, mItems);
			rest_listview.setAdapter(adapter);
			flag = 2;
			btn_all.setBackgroundResource(R.drawable.rest_list_tab);
			btn_down.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_up.setBackgroundResource(R.drawable.rest_list_tab_2);
			break;
		case R.id.rest_upward_btn:
			adapter = new Rest_Adapter(this, mItems_u);
			rest_listview.setAdapter(adapter);
			flag = 1;
			btn_up.setBackgroundResource(R.drawable.rest_list_tab);
			btn_all.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_down.setBackgroundResource(R.drawable.rest_list_tab_2);
			break;
		case R.id.rest_downward_btn:
			adapter = new Rest_Adapter(this, mItems_d);
			rest_listview.setAdapter(adapter);
			flag = 0;
			btn_down.setBackgroundResource(R.drawable.rest_list_tab);
			btn_all.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_up.setBackgroundResource(R.drawable.rest_list_tab_2);
			break;
		case R.id.rest_to_detail_btn:
			Intent it = new Intent(this, Rest_Detail_Activity.class);
			it.putExtra("id", rest_id);
			startActivity(it);
			break;
		case R.id.rest_refresh_bth:
			mItems.clear();
			mItems_u.clear();
			mItems_d.clear();
			flag = 2;
			new Select().execute("http://hax4.woobi.co.kr/restarea.php");
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		NGeoPoint geoPoint = new NGeoPoint();
		poiData.removeAllPOIdata();
		poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
		poiData.beginPOIdata(1);
		// TODO Auto-generated method stub
		switch (flag) {
		case 0:
			geoPoint.set(mItems_d.get(position).longitude, mItems_d.get(position).latitude);
			rest_id = mItems_d.get(position).id;
			poiData.addPOIitem(geoPoint, mItems_d.get(0).name, markerId, 0);
			break;
		case 1:
			geoPoint.set(mItems_u.get(position).longitude, mItems_u.get(position).latitude);
			rest_id = mItems_u.get(position).id;
			poiData.addPOIitem(geoPoint, mItems_u.get(0).name, markerId, 0);
			break;
		case 2:
			geoPoint.set(mItems.get(position).longitude, mItems.get(position).latitude);
			rest_id = mItems.get(position).id;
			poiData.addPOIitem(geoPoint, mItems.get(0).name, markerId, 0);
			break;
		}
		poiData.endPOIdata();
		NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
		poiDataOverlay.showAllPOIdata(0);
		nMapControler.setMapCenter(geoPoint, 13);

	}

}
