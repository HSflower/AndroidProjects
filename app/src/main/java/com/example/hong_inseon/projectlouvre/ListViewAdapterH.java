package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapterH extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<HeartS> heartlist = null;
    private ArrayList<HeartS> arraylist;

    public ListViewAdapterH(Context context, List<HeartS> heartlist) {
        mContext = context;
        this.heartlist = heartlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<HeartS>();
        this.arraylist.addAll(heartlist);
    }

    public class ViewHolder {
        public TextView name1, name2, name3;
        public ImageView p;
    }

    @Override
    public int getCount() {
        return heartlist.size();
    }

    @Override
    public HeartS getItem(int position) {
        return heartlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.heartlist, null);
            holder.name1 = (TextView) view.findViewById(R.id.textMname);
            holder.name2 = (TextView) view.findViewById(R.id.textWname);
            holder.name3 = (TextView) view.findViewById(R.id.textPname);
            holder.p = (ImageView) view.findViewById(R.id.imageH);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name1.setText(heartlist.get(position).getNameM());
        holder.name2.setText(heartlist.get(position).getNameW());
        holder.name3.setText(heartlist.get(position).getNameP());
        holder.p.setImageResource(heartlist.get(position).getImage());

        /*view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, SingleItemView.class);
                intent.putExtra("nameM",(heartlist.get(position).getNameM()));
                intent.putExtra("nameW",(heartlist.get(position).getNameW()));
                intent.putExtra("nameP",(heartlist.get(position).getNameP()));
                intent.putExtra("Image",(heartlist.get(position).getImage()));
                mContext.startActivity(intent);
            }
        });*/

        return view;
    }
}
