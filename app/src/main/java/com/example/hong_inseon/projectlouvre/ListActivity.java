package com.example.hong_inseon.projectlouvre;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //커스텀리스트뷰 사용

        listView = (ListView)findViewById(R.id.ListView);

        ArrayList<String> items = new ArrayList<>();
        items.add("Louvre");
        items.add("Orsay");
        items.add("ABCDE");
        items.add("BCDDD");
        items.add("DFAFSF");
        items.add("예술의 전당");

        CustomAdapter adapter = new CustomAdapter(this, 0, items);
        listView.setAdapter(adapter);
    }

    private class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> items;

        public CustomAdapter(Context context, int textViewResourcedId, ArrayList<String> objects) {
            super(context, textViewResourcedId, objects);
            this.items = objects;
        }

        // position에 위치한 데이터를 화면에 출력하는데 사용될 view를 리턴
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.activity_listview_item, null);
            }


            // 화면에 표시될 view로부터 위젯에 대한 참조 획득
            ImageView imageView = (ImageView)v.findViewById(R.id.imageView2);
            TextView textView = (TextView)v.findViewById(R.id.textView4);

            // 아이템 내 각 위젯에 데이터 반영
            textView.setText(items.get(position));

            return v;
        }

    }
}

