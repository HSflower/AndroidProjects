package com.ourincheon.projectlouvre;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Class411 extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_class411);
    }

    public void onClick2(View view) {
        Intent intent2 = new Intent(this, map.class);
        startActivity(intent2);
    }
}
