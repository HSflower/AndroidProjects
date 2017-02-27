package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Museum> worldmuseumlist = null;
    private ArrayList<Museum> arraylist;

    public ListViewAdapter(Context context, List<Museum> worldmuseumlist) {
        mContext = context;
        this.worldmuseumlist = worldmuseumlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Museum>();
        this.arraylist.addAll(worldmuseumlist);
    }

    public class ViewHolder {
        public TextView name;
    }

    @Override
    public int getCount() {
        return worldmuseumlist.size();
    }

    @Override
    public Museum getItem(int position) {
        return worldmuseumlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(worldmuseumlist.get(position).getName());

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, SingleItemView.class);
                intent.putExtra("name",(worldmuseumlist.get(position).getName()));
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldmuseumlist.clear();
        if (charText.length() == 0) {
            worldmuseumlist.addAll(arraylist);
        }
        else
        {
            for (Museum wp : arraylist)
            {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldmuseumlist.add(wp);
                }
            }
        }
        //notifyDataSetChanged();
    }
}
