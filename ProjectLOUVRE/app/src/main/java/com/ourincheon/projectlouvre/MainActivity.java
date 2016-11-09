package com.ourincheon.projectlouvre;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, SelectClass.class);
                startActivity(intent);
                finish();
            }
        },2000);



    };
    /*public void onClick(View view) {
        Intent intent = new Intent(this,SelectClass.class);
        startActivity(intent);
    }*/
}
