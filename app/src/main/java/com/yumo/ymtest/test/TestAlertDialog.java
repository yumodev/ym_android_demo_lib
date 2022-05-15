package com.yumo.ymtest.test;

import android.app.AlertDialog;

import com.yumo.demo.view.YmTestFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 17/2/12.
 */

public class TestAlertDialog extends YmTestFragment {

    public void testShowDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle("alertDialog")
                .setMessage("message")
                .setPositiveButton("cancel", null)
                .setNegativeButton("ok", null)
                .show();
    }

    public void testListDialog(){
        ArrayList<String> dataList = new ArrayList<>();
        dataList.add("test1");
        dataList.add("test2");
        dataList.add("test3");
        dataList.add("test4");
        showArrayList(dataList);
    }
}
