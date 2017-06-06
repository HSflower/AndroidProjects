package com.example.whererhyu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Start_Activity extends Activity implements OnClickListener{
	
	
	Button btn1, btn2;
	String jsonResult;
	ImageView test;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_layout);
		
		btn1 = (Button)findViewById(R.id.start_to_rest_btn);
		btn2 = (Button)findViewById(R.id.start_to_shelter_btn);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		
		test = (ImageView)findViewById(R.id.test);
		
		//new Select().execute("http://hax4.woobi.co.kr/image_test.php");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_, menu);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent it;
		switch (v.getId()) {
		case R.id.start_to_rest_btn:
			it = new Intent(this, Rest_Activity.class);
			startActivity(it);
			break;
		case R.id.start_to_shelter_btn:
			it = new Intent(this, Shelter_Activity.class);
			startActivity(it);
			break;
		}
		
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
				JSONArray jsonMainNode = jsonResponse.optJSONArray("image");
				if (jsonMainNode != null) {
					for (int i = 0; i < jsonMainNode.length(); i++) {
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
						String temp_s = jsonChildNode.optString("image");
						byte[] temp = Base64.decode(temp_s, Base64.DEFAULT);
						Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
						test.setImageBitmap(bitmap);
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
				//objValuePair.add(new BasicNameValuePair("rest_id", rest_id));

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
