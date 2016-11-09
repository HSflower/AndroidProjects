package com.ourincheon.projectlouvre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class map extends AppCompatActivity {
    public double x, y;//비콘에서 받은 거리를 통해 받은 거리를 함수에 입력해 받은 x, y값
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Point p = new Point(this);
        //setContentView(p);
        setContentView(R.layout.activity_map);
    }

    /*ImageView iv = (ImageView)findViewById(R.id.imageView2);

    public void toViewRawXY(View view) {
        View parentView = view.getRootView();
        int sumX = 0;
        int sumY = 0;

        boolean chk = false;
        while(!chk) {
            sumX = sumX + view.getLeft();
            sumY = sumY + view.getTop();

            view = (View)view.getParent();
            if(parentView == view) {
                chk = true;
            }
            System.out.println(sumX);
            System.out.println(sumY);
        }
    }*/

    public void onClick3(View view) {
        Intent intent3 = new Intent(this, popupText.class);
        startActivity(intent3);
    }

    /*public void showTimelineMinus(float x) {
        RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams((int) dpToPx(35.3f), (int) dpToPx(52.6f)); // width, height
        layoutParms.setMargins((int) dpToPx(x), (int) dpToPx(246f), 0, 0); // left, top, 0, 0
        iv.setLayoutParams(layoutParms);
        iv.setVisibility(View.VISIBLE);
    }

    public float dpToPx(float dp){
        float px =  TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                this.getResources().getDisplayMetrics());
        return px;
    }*/
}
