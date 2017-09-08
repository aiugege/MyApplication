package com.aiugege.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class GuideActivity extends AppCompatActivity {
    private ImageView mIv;
    private TextView mTvTime;
    private boolean isSkip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mIv = (ImageView) findViewById(R.id.image_guide);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        Glide.with(this)
                .load(R.drawable.guide_bg)
                // 设置高斯模糊
                .bitmapTransform(new BlurTransformation(this, 25, 4))
                .into(mIv);
        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(final long millisUntilFinished) {
                mTvTime.setText(millisUntilFinished / 1000 + " s");
                mTvTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isSkip = true;
                        Toast.makeText(GuideActivity.this,millisUntilFinished / 1000 + " s",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GuideActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }

            @Override
            public void onFinish() {
                if (!isSkip){
                    setupWindowAnimations();
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    finish();
                }

            }
        }.start();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(5000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(fade);
        }
    }
}
