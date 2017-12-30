package com.wdy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.library.activity.WDYBaseActivity;


/**
 * 作者：王东一
 * 创建时间：2017/12/4.
 */

public class TestActivity extends WDYBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
