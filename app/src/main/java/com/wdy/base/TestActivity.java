package com.wdy.base;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.base.library.activity.WDYBaseActivity;
import com.base.library.application.BaseApplication;

import org.greenrobot.eventbus.EventBus;


/**
 * 作者：王东一
 * 创建时间：2017/12/4.
 */

public class TestActivity extends WDYBaseActivity {
    private ImageView imageView;
    private Animation hyperspaceJumpAnimation;
    private CountDownTimer timer;//验证码定时器
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        hyperspaceJumpAnimation  =AnimationUtils.loadAnimation(this, R.anim.bounce_interpolator_test);
        initTimer(3);
        timer.start();
    }
    private void initTimer(long time) {
        //编辑定时器
        timer = new CountDownTimer(time * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {
                imageView.startAnimation(hyperspaceJumpAnimation);
                timer.start();
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        timer.cancel();
    }

}
