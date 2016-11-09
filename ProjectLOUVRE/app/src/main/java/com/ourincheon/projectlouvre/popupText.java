package com.ourincheon.projectlouvre;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class popupText extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //equestWindowFeature(Window.FEATURE_NO_TITLE);
        //WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        //layoutParams.flags =  WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //layoutParams.dimAmount = 0.7f;
        //getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_popup_text);
    }

    public void onClickClosePopup(View v) {
        switch(v.getId())
        {
            case R.id.button7:
            this.finish();
                    break;
        }
    }

}
