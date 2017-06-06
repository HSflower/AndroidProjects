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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class Rest_Info_Image_Fragment_2 extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	View rootView;
	String jsonResult;
	String rest_id;

	public static Rest_Info_Image_Fragment_2 newInstance(int sectionNumber) {
		Rest_Info_Image_Fragment_2 fragment = new Rest_Info_Image_Fragment_2();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public Rest_Info_Image_Fragment_2() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.rest_info_image_fragment_1,
				container, false);
		rest_id = Rest_Detail_Activity.rest_id;
		new Select().execute("http://hax4.woobi.co.kr/rest_image.php");
		return rootView;
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
				JSONArray jsonMainNode = jsonResponse.optJSONArray("image");
				if (jsonMainNode != null) {
					for (int i = 0; i < jsonMainNode.length(); i++) {
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
						String temp_s = jsonChildNode.getString("image2");
						byte[] temp = Base64.decode(temp_s, Base64.DEFAULT);
						Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
						ImageView im = (ImageView) rootView.findViewById(R.id.rest_info_image_1);
						im.setImageBitmap(bitmap);
					}
				}

			} catch (JSONException e) {
				Toast.makeText(getActivity().getApplicationContext(),
						"Error" + e.toString(), Toast.LENGTH_LONG).show();
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

}
