package com.yumo.ymtest;

import com.yumo.demo.entry.YmPackageInfo;
import com.yumo.demo.listener.IGetPackageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YumoDev on 17/2/12.  
 */

public class TestPackageData implements IGetPackageData {

    @Override
    public List<YmPackageInfo> getPackageList() {
        List<YmPackageInfo> list = new ArrayList<YmPackageInfo>();
        list.add(new YmPackageInfo("test", "com.yumo.ymtest.test"));
        list.add(new YmPackageInfo("web", "com.yumo.ymtest.web"));
        list.add(new YmPackageInfo("sys", "com.yumo.ymtest.sys"));
        list.add(new YmPackageInfo("kotlin", "com.yumo.ymtest.kotlin"));
        return list;
    }
}
