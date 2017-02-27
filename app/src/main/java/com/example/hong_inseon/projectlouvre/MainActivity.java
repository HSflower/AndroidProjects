package com.example.hong_inseon.projectlouvre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, ExhibitionInfoActivity.class);
        startActivity(intent);
    }

    public void onClick2(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClick3(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
