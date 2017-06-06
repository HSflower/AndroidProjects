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

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class Shelter_Activity extends NMapActivity implements OnClickListener, OnItemClickListener,OnMapStateChangeListener{
	
	LinearLayout MapContainer;
	NMapView nMap;
	NMapController nMapControler;
	private NMapViewerResourceProvider mMapViewerResourceProvider;
	private NMapOverlayManager mOverlayManager;
	NMapPOIdata poiData;
	int markerId;
	
	public static final String API_KEY = "8dded4fa55857775e913549a5ec8e488";
	
	private String jsonResult;
	
	ListView shelter_listview;
	ArrayList<Shelter_Item> mItems, mItems_u, mItems_d;
	Shelter_Adapter adapter;
	
	Select select;
	
	int flag = 2;
	Button btn_up, btn_down, btn_all, btn_refresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shelter_layout);
		
		MapContainer = (LinearLayout)findViewById(R.id.shelter_map_linear);
		shelter_listview = (ListView)findViewById(R.id.shelter_list_listview);
		shelter_listview.setOnItemClickListener(this);
		
		btn_all = (Button) findViewById(R.id.shelter_all_btn);
		btn_up = (Button) findViewById(R.id.shelter_upward_btn);
		btn_down = (Button) findViewById(R.id.shelter_downward_btn);
		btn_refresh = (Button) findViewById(R.id.shelter_refresh_btn);

		btn_all.setOnClickListener(this);
		btn_up.setOnClickListener(this);
		btn_down.setOnClickListener(this);
		btn_refresh.setOnClickListener(this);
		
		mItems = new ArrayList<Shelter_Item>();
		mItems.clear();
		mItems_u = new ArrayList<Shelter_Item>();
		mItems_u.clear();
		mItems_d = new ArrayList<Shelter_Item>();
		mItems_d.clear();
		
		adapter = new Shelter_Adapter(this, mItems);
		shelter_listview.setAdapter(adapter);
		
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
		
		select = new Select();
		select.execute("http://hax4.woobi.co.kr/shelter.php");
		
	}
	
	class Select extends AsyncTask<String, Void, String> {
		private StringBuilder inputStreamToString(InputStream is) throws IOException {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,"EUC-KR"));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}
		
		public void ListDrwaer() {
			try {
				JSONObject jsonResponse = new JSONObject(jsonResult);
				JSONArray jsonMainNode = jsonResponse.optJSONArray("shelter");
				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
					String id = jsonChildNode.optString("shel_id");
					String name = jsonChildNode.optString("shel_name");
					float latitude = (float) jsonChildNode.optDouble("s_latitude");
					float longitude = (float) jsonChildNode.optDouble("s_longitude");
					int updown = jsonChildNode.optInt("s_updownward");
					mItems.add(new Shelter_Item(id, name, latitude, longitude, updown));
					if (updown == 0 || updown == 2) {
						mItems_d.add(new Shelter_Item(id, name, latitude, longitude, updown));
					}
					if (updown == 1 || updown == 2) {
						mItems_u.add(new Shelter_Item(id, name, latitude, longitude, updown));
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
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),Toast.LENGTH_LONG).show();
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
	
	@Override
	public void onAnimationStateChange(NMapView arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapCenterChange(NMapView arg0, NGeoPoint arg1) {
		// TODO Auto-generated method stub
		//nMapControler.setMapCenter(arg1, 11);
		
	}

	@Override
	public void onMapCenterChangeFine(NMapView arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapInitHandler(NMapView View, NMapError errorInfo) {
		// TODO Auto-generated method stub
		if(errorInfo == null) {
			//경도/longitude 위도/latitude 순으로 입력
			nMapControler.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);
		}
		else {
			Log.e("NMAP", "onMapInitHandler : error = "+errorInfo.toString());
		}
		
	}

	@Override
	public void onZoomLevelChange(NMapView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shelter_all_btn:
			adapter = new Shelter_Adapter(this, mItems);
			shelter_listview.setAdapter(adapter);
			flag = 2;
			btn_all.setBackgroundResource(R.drawable.rest_list_tab);
			btn_down.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_up.setBackgroundResource(R.drawable.rest_list_tab_2);
			break;
		case R.id.shelter_upward_btn:
			adapter = new Shelter_Adapter(this, mItems_u);
			shelter_listview.setAdapter(adapter);
			flag = 1;
			btn_up.setBackgroundResource(R.drawable.rest_list_tab);
			btn_all.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_down.setBackgroundResource(R.drawable.rest_list_tab_2);
			break;
		case R.id.shelter_downward_btn:
			adapter = new Shelter_Adapter(this, mItems_d);
			shelter_listview.setAdapter(adapter);
			flag = 0;
			btn_down.setBackgroundResource(R.drawable.rest_list_tab);
			btn_all.setBackgroundResource(R.drawable.rest_list_tab_2);
			btn_up.setBackgroundResource(R.drawable.rest_list_tab_2);
			break;
		case R.id.shelter_refresh_btn:
			mItems.clear();
			mItems_u.clear();
			mItems_d.clear();
			flag = 2;
			new Select().execute("http://hax4.woobi.co.kr/shelter.php");
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		NGeoPoint geoPoint = new NGeoPoint();
		poiData.removeAllPOIdata();
		poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
		poiData.beginPOIdata(1);
		switch (flag) {
		case 0:
			geoPoint.set(mItems_d.get(position).longitude, mItems_d.get(position).latitude);
			poiData.addPOIitem(geoPoint, mItems_d.get(0).name, markerId, 0);
			break;
		case 1:
			geoPoint.set(mItems_u.get(position).longitude, mItems_u.get(position).latitude);
			poiData.addPOIitem(geoPoint, mItems_u.get(0).name, markerId, 0);
			break;
		case 2:
			geoPoint.set(mItems.get(position).longitude, mItems.get(position).latitude);
			poiData.addPOIitem(geoPoint, mItems.get(0).name, markerId, 0);
			break;
		}
		poiData.endPOIdata();
		NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
		poiDataOverlay.showAllPOIdata(0);
		nMapControler.setMapCenter(geoPoint, 13);

		
	}

}
