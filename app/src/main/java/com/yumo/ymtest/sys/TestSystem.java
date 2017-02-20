package com.yumo.ymtest.sys;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.yumo.demo.view.YmTestFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by YmMoDev on 17/2/12.
 */

public class TestSystem extends YmTestFragment{

    private final String LOG_TAG = "TestSystem";


    public void testAvailMemory(){
        showToastMessage(formatByte(getAvailMemory(getContext())));
    }


    public long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);

        return mi.availMem;
    }

    /**
     * TODO 将字节转换为B、KB、MB、GB、TB主要用于内存，文件大小的转换，格式化
     * yumo
     *
     * @param lByte
     * @return String
     * 2015-3-14
     */
    public static String formatByte(long lByte) {
        String result = "";
        do {

            if (lByte < 1024) {
                result = String.valueOf(lByte) + "B";
            } else if (lByte < 1024 * 1024) {
                result = String.valueOf(lByte / 1024) + "KB";
            } else if (lByte < 1024 * 1024 * 1024) {
                result = String.valueOf(lByte / (1024 * 1024)) + "MB";
            } else if (lByte < 1024 * 1024 * 1024 * 1024) {
                long k = lByte / (1024 * 1024 * 1024);
                result = String.valueOf(k) + "GB";
            } else {
                result = String.valueOf(lByte / (1024 * 1024 * 1024 * 1024)) + "TB";
            }

        } while (false);

        return result;
    }
}
