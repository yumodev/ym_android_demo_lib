package com.yumo.demo.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.yumo.demo.R;

/**
 * Created by yumodev on 15/11/18.
 * 测试Activity。
 */
public class YmTestActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ymtest_test_page);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.test_fragment_id, new YmTestPackageFragment());
        transaction.commit();
    }
}
