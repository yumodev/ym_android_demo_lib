package com.yumo.ymtest.sys;

import android.app.ActivityManager;
import android.content.Context;

import com.yumo.demo.anno.YmClassTest;
import com.yumo.demo.anno.YmMethodTest;
import com.yumo.demo.view.YmTestFragment;

import java.math.BigDecimal;

/**
 * Created by YmMoDev on 17/2/12.
 */

@YmClassTest(name = "测试安卓系统信息")
public class TestSystem extends YmTestFragment{

    private final String LOG_TAG = "TestSystem";


    @YmMethodTest(name = "显示可用内存")
    public void testAvailMemory(){
        showToastMessage(formatByte(getAvailMemory(getContext())));
    }


    public long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);

        return mi.availMem;
    }

//    /**
//     * TODO 将字节转换为B、KB、MB、GB、TB主要用于内存，文件大小的转换，格式化
//     * yumo
//     *
//     * @param lByte
//     * @return String
//     * 2015-3-14
//     */
//    public static String formatByte(long lByte) {
//        String result = "";
//
//        if (lByte < 1024) {
//            result = lByte+ "B";
//        } else if (lByte < 1024 * 1024) {
//            result = lByte / 1024+ "KB";
//        } else if (lByte < 1024 * 1024 * 1024) {
//            result =lByte / (1024 * 1024) + "MB";
//        } else if (lByte < 1024 * 1024 * 1024 * 1024) {
//            long k = lByte / (1024 * 1024 * 1024);
//            result = k + "GB";
//        } else {
//            result = lByte / (1024 * 1024 * 1024 * 1024) + "TB";
//        }
//        return result;
//    }

    /**
     * 文件大小格式化
     * @param size 单位为B、kb、mb、gb转换
     * @return
     */
    public static String formatByte(long size){
        long kb = 1024;
        long mb = kb*1024;
        long gb = mb*1024;
        if (size >= gb){
            return div(size+"",gb+"",2)+"GB";
        }else if (size >= mb){
            return div(size+"",mb+"",2)+"MB";

        }else if (size > kb){
            return div(size+"",kb+"",2)+"KB";
        }else {
            return div(size+"",1+"",2)+"B";
        }
    }




    /**
     * 除法运算
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static String div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal divide = b1.divide(b2, scale, BigDecimal.ROUND_HALF_DOWN);
        return divide.toString();
    }
}
