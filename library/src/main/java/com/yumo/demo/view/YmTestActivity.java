package com.yumo.demo.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.yumo.demo.R;

/**
 * Created by yumodev on 15/11/18.
 */
public class YmTestActivity extends AppCompatActivity {
    private final String LOG_TAG = "YmTestActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_page);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.test_fragment_id, new YmTestPackageFragment());
        transaction.commit();
    }
}
