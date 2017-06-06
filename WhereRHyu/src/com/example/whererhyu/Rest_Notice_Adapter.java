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
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Rest_Notice_Adapter extends BaseAdapter implements OnClickListener{

	private LayoutInflater inflater = null;
	public static ViewHolder holder = null;
	private ArrayList<Rest_Notice_Item> mItems = null;
	private Rest_Notice_Item tempItem = null;
	private Context mContext = null;
	
	String jsonResult;
	int pnum = 0;
	
	class ViewHolder {
		TextView title;
		Button image_icon;
		TextView time;
		TextView cont;
		Button reply_icon;
		LinearLayout reply_layout;
		Button write_reply_button;
		
	}
	
	public Rest_Notice_Adapter(Context context, ArrayList<Rest_Notice_Item> array) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mItems = array;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		if(mItems.get(position).isphoto) {
			tempItem = new Rest_Notice_Item(mItems.get(position).pnum, mItems.get(position).title, mItems.get(position).time, mItems.get(position).cont, mItems.get(position).photo);
		} else {
			tempItem = new Rest_Notice_Item(mItems.get(position).pnum, mItems.get(position).title, mItems.get(position).time, mItems.get(position).cont);
		}
		if(v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.rest_notice_item, parent, false);
			holder.title = (TextView) v.findViewById(R.id.notice_item_title_tv);
			holder.image_icon = (Button) v.findViewById(R.id.notice_item_image_icon_btn);
			holder.time = (TextView) v.findViewById(R.id.notice_item_time_tv);
			holder.cont = (TextView) v.findViewById(R.id.notice_item_content_tv);
			holder.reply_icon = (Button) v.findViewById(R.id.notice_item_reply_icon_btn);
			
			holder.title.setText(tempItem.title);
			holder.time.setText(tempItem.time);
			holder.cont.setText(tempItem.cont);
			holder.reply_icon.setOnClickListener(this);
			if(tempItem.isphoto) {
				holder.image_icon.setOnClickListener(this);
			} else {
				holder.image_icon.setVisibility(View.GONE);
			}
			
			holder.title.setTag(position);
			holder.image_icon.setTag(position);
			holder.time.setTag(position);
			holder.cont.setTag(position);
			holder.reply_icon.setTag(position);
			
			v.setTag(holder);
			
		} else {
			holder = (ViewHolder) v.getTag();
			holder.title.setText(tempItem.title);
			holder.time.setText(tempItem.time);
			holder.cont.setText(tempItem.cont);
			holder.reply_icon.setOnClickListener(this);
			if(tempItem.isphoto) {
				holder.image_icon.setOnClickListener(this);
			} else {
				holder.image_icon.setVisibility(View.INVISIBLE);
			}
			
			holder.title.setTag(position);
			holder.image_icon.setTag(position);
			holder.time.setTag(position);
			holder.cont.setTag(position);
			holder.reply_icon.setTag(position);
			
			v.setTag(holder);
		}
		return v;
	}

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.notice_item_reply_icon_btn) {
			tempItem = mItems.get((int)((Integer)v.getTag()));
			pnum = tempItem.pnum;
			new Select2().execute("http://hax4.woobi.co.kr/noti_reply.php");
			Rest_Notice_Fragment.listview_noti.setVisibility(View.INVISIBLE);
			Rest_Notice_Fragment.linear_reply_view.setVisibility(View.VISIBLE);
		}
		
	}
	class Select2 extends AsyncTask<String, Void, String> {
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
				/*Toast.makeText(mContext,
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();*/
			}
			return answer;
		}

		public void ListDrwaer() {
			try {
				JSONObject jsonResponse = new JSONObject(jsonResult);
				JSONArray jsonMainNode = jsonResponse.optJSONArray("noti_reply");
				if (jsonMainNode != null) {
					Rest_Notice_Fragment.rItems.clear();
					for (int i = 0; i < jsonMainNode.length(); i++) {
						JSONObject jsonChildNode = jsonMainNode	.getJSONObject(i);
						int num = jsonChildNode.optInt("num");
						String name = jsonChildNode.optString("name");
						String time = jsonChildNode.optString("time");
						String cont = jsonChildNode.optString("cont");
						Rest_Notice_Fragment.rItems.add(new Rest_Notice_Reply_Item(num, name, time, cont));
					}
					Rest_Notice_Fragment.r_adapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				/*Toast.makeText(mContext, "Error" + e.toString(),
						Toast.LENGTH_LONG).show();*/
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			Vector<NameValuePair> objValuePair = new Vector<NameValuePair>();
			objValuePair.add(new BasicNameValuePair("pnum", ""+pnum));

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
