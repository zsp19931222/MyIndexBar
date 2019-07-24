package com.zsp.myindexbar;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * description:
 * author:created by Andy on 2019/7/19 0019 12:00
 * email:zsp872126510@gmail.com
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
