package com.yumo.ymtest.test;

import android.app.AlertDialog;

import com.yumo.demo.view.YmTestFragment;

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
}
