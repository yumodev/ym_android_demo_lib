package com.yumo.ymtest;

import android.app.Application;

import com.yumo.demo.utils.YmUIDemoManager;

/**
 * Created by yumodev on 17/7/10.
 */

public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YmUIDemoManager.getInstance().setAppPackageName(getPackageName());
    }
}
