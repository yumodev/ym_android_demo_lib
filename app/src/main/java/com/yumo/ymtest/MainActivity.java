package com.yumo.ymtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.yumo.demo.view.YmTestActivity;
import com.yumo.demo.view.YmTestView;
import com.yumo.ymtest.base.BaseActivity;
import com.yumo.ymtest.test.TestViewDialog;

public class MainActivity extends BaseActivity{

    protected Toolbar mToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        YmTestView testView = (YmTestView) findViewById(R.id.test_view);
        testView.init(this, getClass());

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
    }

    /**
     * 分包名进行demo测试。
     */
    public void testAllTestFragmentInApp(){
        startActivity(new Intent(MainActivity.this, YmTestActivity.class));
    }

    /**
     * 测试YmTestView类的使用
     */
    public void testYmTestView(){
        TestViewDialog fragment = new TestViewDialog();
        fragment.show(getSupportFragmentManager(), "test_view");
    }
}
