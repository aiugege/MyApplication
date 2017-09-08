package com.aiugege.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ViewFlipper;

/**
 * Created by qiji on 2017/1/20 15:30.
 */

public class MarqueeviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marqueeview);

        ViewFlipper vf = (ViewFlipper) findViewById(R.id.vf);

        vf.addView(View.inflate(this, R.layout.view_advertisement01, null));
        vf.addView(View.inflate(this, R.layout.view_advertisement02, null));
        vf.addView(View.inflate(this, R.layout.view_advertisement03, null));
    }
}
