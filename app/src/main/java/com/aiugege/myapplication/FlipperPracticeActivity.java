package com.aiugege.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/**
 * Created by qiji on 2017/7/19 14:32.
 */

public class FlipperPracticeActivity extends AppCompatActivity {
    private ViewFlipper flipper;
    private GestureDetector detector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flipper_practice);
        init();
    }

    private void init() {
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.addView(getImageView(R.mipmap.img_1));
        flipper.addView(getImageView(R.mipmap.img_2));
        flipper.addView(getImageView(R.mipmap.img_3));
        flipper.addView(getImageView(R.mipmap.img_4));
        flipper.setInAnimation(inFromRightAnimation());
        flipper.setOutAnimation(outToLeftAnimation());
        flipper.setFlipInterval(2000);
        flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });


        detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
            /*
             * 用户按下触摸屏、快速移动后松开即触发这个事件
             * e1：第1个ACTION_DOWN MotionEvent
             * e2：最后一个ACTION_MOVE MotionEvent
             * velocityX：X轴上的移动速度，像素/秒
             * velocityY：Y轴上的移动速度，像素/秒
             * 触发条件 ：
             * X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                final float FLING_MIN_DISTANCE = 100;//最小滑动像素
                final float FLING_MIN_VELOCITY = 150;//最小滑动速度
                if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE &&
                        Math.abs(velocityX) > FLING_MIN_VELOCITY){//X轴上的移动速度去绝对值进行比较
                    //判断x轴坐标如果第一次按下时的坐标减去第二次离开屏幕时的坐标大于我们设置的位移，因为一个控件的原点是在左上角，就说明此时是向左滑动的
                    //设置View进入屏幕时候使用的动画
                    flipper.setInAnimation(inFromRightAnimation());
                    //设置View退出屏幕时候使用的动画
                    flipper.setOutAnimation(outToLeftAnimation());
                    flipper.showNext();//显示下一个视图

                }else if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE &&
                        Math.abs(velocityX) > FLING_MIN_VELOCITY){
                    //判断x轴坐标如果第二次离开屏幕时的坐标减去第一次按下时的坐标大于我们设置的位移，因为一个控件的原点是在左上角，就说明此时是向右滑动的
                    flipper.setInAnimation(inFromLeftAnimation());
                    flipper.setOutAnimation(outToRightAnimation());
                    flipper.showPrevious();//显示上一个视图
                }
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                //手指按下屏幕时触发
                flipper.stopFlipping();//当触摸到ViewFlipper时，就让它停止自动滑动，如果要触摸到整个屏幕的任意一处就让它停止滑动，就按上面那个方法，不用设置ViewFlipper的触摸事件监听了
                new Handler().postDelayed(new Runnable() {//在当前线程（也即主线程中）开启一个消息处理器，并在3秒后发送flipper.startFlipping();这个消息给主线程，再让它自动滑动，从而来更新UI
                    @Override
                    public void run() {
                        flipper.startFlipping();//3秒后执行，让它又开始滑动
                    }
                }, 3000);
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                flipper.getCurrentView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FlipperPracticeActivity.this,MarqueeviewActivity.class);
                        startActivity(intent);
                    }
                });
                return super.onSingleTapUp(e);
            }
        });
    }

    //创建一个ImageView对象
    private ImageView getImageView(int id){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        return imageView;
    }

    /**
     * 定义从右侧进入的动画效果
     * @return
     */
    protected Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(200);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    /**
     * 定义从左侧退出的动画效果
     * @return
     */
    protected Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(200);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    /**
     * 定义从左侧进入的动画效果
     * @return
     */
    protected Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(200);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    /**
     * 定义从右侧退出时的动画效果
     * @return
     */
    protected Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(200);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }
}
