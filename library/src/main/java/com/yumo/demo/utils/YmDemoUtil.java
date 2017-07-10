package com.yumo.demo.utils;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by yumodev on 17/7/10.
 */

public class YmDemoUtil {
    private static String mAppPackage = "";

    public static void setAppPackageName(String packageName){
        mAppPackage = packageName;
    }

    public static String getAppPackageName(Context context){
        if (TextUtils.isEmpty(mAppPackage)){
            return context.getPackageName();
        }
        return mAppPackage;
    }
}
