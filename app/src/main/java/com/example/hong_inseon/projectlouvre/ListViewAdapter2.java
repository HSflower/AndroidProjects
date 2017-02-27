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

public class ListViewAdapter2 extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Exhibition> worldexhibitionlist = null;
    private ArrayList<Exhibition> arraylist2;

    public ListViewAdapter2(Context context, List<Exhibition> worldexhibitionlist) {
        mContext = context;
        this.worldexhibitionlist = worldexhibitionlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist2 = new ArrayList<Exhibition>();
        this.arraylist2.addAll(worldexhibitionlist);
    }

    public class ViewHolder {
        public TextView name;
    }

    @Override
    public int getCount() {
        return worldexhibitionlist.size();
    }

    @Override
    public Exhibition getItem(int position) {
        return worldexhibitionlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item2, null);
            holder.name = (TextView) view.findViewById(R.id.name2);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(worldexhibitionlist.get(position).getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, SingleItemView.class);
                intent.putExtra("name",(worldexhibitionlist.get(position).getName()));
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldexhibitionlist.clear();
        if (charText.length() == 0) {
            worldexhibitionlist.addAll(arraylist2);
        }
        else
        {
            for (Exhibition wp : arraylist2)
            {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldexhibitionlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
