package com.example.hong_inseon.projectlouvre;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class OptionP extends AppCompatActivity {
    ListView list, list2;
    ListViewAdapter adapter;
    ListViewAdapter2 adapter2;
    EditText editsearch;
    TextView t,t1,t2;
    String[] name, name2;
    ArrayList<Museum> arraylist = new ArrayList<Museum>();
    ArrayList<Exhibition> arraylist2 = new ArrayList<Exhibition>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_p);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs);
        //액션바에서 가운데 맞춤을 해주는 것
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yourimage));
        //위에는 이미지를 넣고 싶을때 사용

        name = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan", "Qwerty", "Asdfghjk", "Zxcvbn", "Qsxwdvf" };

        name2 = new String[] {"Egypt Treasure", "Alphons Muha", "Hundaert Muha",
                "Mordern Architect", "Fornasetti Pract", "lnuar's Lady","Youth", "Look Smithsonian",
                "Magic Forest", "kid's Plex", " Meeting with Toy in the World", "Ansan Colorful Plant"};

        list = (ListView) findViewById(R.id.listview);
        list2 = (ListView)findViewById(R.id.listview2);
        t = (TextView)findViewById(R.id.Result);
        t1 = (TextView)findViewById(R.id.textMuseum);
        t2 = (TextView)findViewById(R.id.textExhibition);

        for (int i = 0; i < name.length; i++)
        {
            Museum wp = new Museum(name[i]);
            arraylist.add(wp);
        }

        for (int i = 0; i < name2.length; i++) {
            Exhibition wp2 = new Exhibition(name2[i]);
            arraylist2.add(wp2);
        }

        adapter = new ListViewAdapter(this, arraylist);
        adapter2 = new ListViewAdapter2(this, arraylist2);

        list.setAdapter(adapter);
        list2.setAdapter(adapter2);
        editsearch = (EditText) findViewById(R.id.search);
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
// TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                t1.setVisibility(INVISIBLE);
                t2.setVisibility(INVISIBLE);
                list.setVisibility(INVISIBLE);
                list2.setVisibility(INVISIBLE);
                adapter.filter(text);
                adapter2.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
// TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
// TODO Auto-generated method stub
            }
        });
    }

    public void find(View v) {
        t.setText("찾은 검색 : 박물관 " + adapter.getCount() + "건, 전시회 " +adapter2.getCount() + "건");
        t1.setVisibility(VISIBLE);
        t2.setVisibility(VISIBLE);
        list.setVisibility(VISIBLE);
        list2.setVisibility(VISIBLE);
        Utility.setListViewHeightBasedOnChildren(list);
        Utility.setListViewHeightBasedOnChildren(list2);
    }

    public static class Utility {
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }
    //리스트의 크기를 구해 전체 크기를를 맞춰주는 함수
}
