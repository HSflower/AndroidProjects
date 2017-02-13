package com.example.hong_inseon.projectlouvre;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ListviewItem extends AppCompatActivity {

    ListView listView;

    private Drawable iconDrawable;
    private String titleStr;

    public void setIcon(Drawable icon){
        iconDrawable = icon;
    }

    public void setTitlename(String title){
        titleStr = title;
    }

    public Drawable getIcon(){
        return this.iconDrawable;
    }

    public String getTitlename(){
        return this.titleStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_item);

        listView = (ListView)findViewById(R.id.ListView);
    }
}