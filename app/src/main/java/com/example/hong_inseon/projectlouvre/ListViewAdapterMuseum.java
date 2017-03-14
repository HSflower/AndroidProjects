package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hong_inseon.projectlouvre.dao.DataDAO;
import com.example.hong_inseon.projectlouvre.dao.Museum;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapterMuseum extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Museum> worldmuseumlist = null;
    private ArrayList<Museum> arraylist;
    DataDAO msList = new DataDAO();

    public ListViewAdapterMuseum(Context context, List<Museum> worldmuseumlist) {
        mContext = context;
        this.worldmuseumlist = worldmuseumlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Museum>();
        this.arraylist.addAll(worldmuseumlist);
    }

    public class ViewHolder {
        public TextView nameM, nameA;
        public RatingBar rating;
        public ImageView Image;
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
            view = inflater.inflate(R.layout.museumviewholder, null);
            holder.nameM = (TextView) view.findViewById(R.id.textMname2);
            holder.nameA = (TextView) view.findViewById(R.id.textAname);
            holder.rating = (RatingBar)view.findViewById(R.id.textRname);
            holder.Image = (ImageView)view.findViewById(R.id.imageMuseum);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.nameM.setText(worldmuseumlist.get(position).getMs_name());
        holder.nameA.setText(worldmuseumlist.get(position).getMs_address());
        holder.rating.setRating(worldmuseumlist.get(position).getMs_rating());
        holder.Image.setImageResource(worldmuseumlist.get(position).getMs_image());

        /*view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, SingleItemView.class);
                intent.putExtra("name",(worldmuseumlist.get(position).getName()));
                mContext.startActivity(intent);
            }
        });*/

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
                if (wp.getMs_name().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldmuseumlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    //버튼을 눌렀을때 포함한 문자가 있을때 이를 리스트에 띄워 주는 메소드
}
