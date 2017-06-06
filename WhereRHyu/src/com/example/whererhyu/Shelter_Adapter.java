package com.example.whererhyu;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhn.android.maps.maplib.NGeoPoint;

public class Shelter_Adapter extends BaseAdapter{

	private LayoutInflater inflater = null;
	public static ViewHolder holder = null;
	private ArrayList<Shelter_Item> mItem = null;
	private Context mContext = null;

	private class ViewHolder {
		public LinearLayout layout = null;
		public TextView name;
		public ImageView icon;
	}
	
	public Shelter_Adapter(Context context, ArrayList<Shelter_Item> array) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.inflater = LayoutInflater.from(this.mContext);
		this.mItem = array;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItem.get(position);
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
		Shelter_Item item = new Shelter_Item(mItem.get(position).id,
				mItem.get(position).name, mItem.get(position).latitude,
				mItem.get(position).longitude, mItem.get(position).updown);
		if(v == null) {
			holder = new ViewHolder();
			inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.shelter_item, null);
			holder.layout = (LinearLayout) v.findViewById(R.id.shelter_item_ll);
			holder.name = (TextView)v.findViewById(R.id.shelter_item_name_tv);
			
			holder.name.setText(item.name);
			
			holder.layout.setTag(position);
			holder.name.setTag(position);
			
			v.setTag(holder);
		}
		else {
			holder = (ViewHolder)v.getTag();
			holder.name.setText(item.name);
			
			holder.layout.setTag(position);
			holder.name.setTag(position);
			
			v.setTag(holder);
		}
		return v;
	}

}
