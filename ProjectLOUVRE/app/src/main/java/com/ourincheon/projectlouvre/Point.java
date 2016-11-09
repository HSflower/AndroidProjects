package com.ourincheon.projectlouvre;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 박명준 on 2016-10-31.
 */

public class Point extends View {
    private int x,y;

    public Point(Context context) {
        super(context);
    }

    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX(0);
        y = (int) event.getY(0);
        invalidate();
        return super.onTouchEvent(event);
    }

    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawCircle(x,y,20,paint);
    }
}
