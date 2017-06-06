package com.example.whererhyu;

import java.util.ArrayList;

import com.example.whererhyu.Rest_Notice_Adapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class Rest_Notice_Reply_Adapter extends BaseAdapter implements OnClickListener{
	
	private LayoutInflater inflater = null;
	public static ViewHolder holder = null;
	private ArrayList<Rest_Notice_Reply_Item> mItems = null;
	private Rest_Notice_Reply_Item tempItem = null;
	private Context mContext = null;
	
	class ViewHolder {
		TextView name;
		TextView time;
		TextView cont;
		Button delete;
		
	}
	
	public Rest_Notice_Reply_Adapter(Context context, ArrayList<Rest_Notice_Reply_Item> array) {
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
		tempItem = new Rest_Notice_Reply_Item(mItems.get(position).num, mItems.get(position).name, mItems.get(position).time, mItems.get(position).cont);
		if(v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.rest_notice_reply_item, parent, false);
			holder.name = (TextView)v.findViewById(R.id.rest_notice_reply_item_name);
			holder.time = (TextView)v.findViewById(R.id.rest_notice_reply_item_time);
			holder.cont = (TextView)v.findViewById(R.id.rest_notice_reply_item_cont);
			holder.delete = (Button)v.findViewById(R.id.rest_notice_reply_item_delete);
			
			holder.name.setText(tempItem.name);
			holder.time.setText(tempItem.time);
			holder.cont.setText(tempItem.cont);
			holder.delete.setOnClickListener(this);
			
			holder.name.setTag(position);
			holder.time.setTag(position);
			holder.cont.setTag(position);
			holder.delete.setTag(position);
			
			v.setTag(holder);
			
		} else {
			holder = (ViewHolder) v.getTag();
			holder.name.setText(tempItem.name);
			holder.time.setText(tempItem.time);
			holder.cont.setText(tempItem.cont);
			holder.delete.setOnClickListener(this);
			
			holder.name.setTag(position);
			holder.time.setTag(position);
			holder.cont.setTag(position);
			holder.delete.setTag(position);
			v.setTag(holder);
			
		}
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
