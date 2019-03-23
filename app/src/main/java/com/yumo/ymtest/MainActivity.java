package com.yumo.ymtest;

import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import com.yumo.demo.anno.YmMethodTest;
import com.yumo.demo.utils.YmUIDemoManager;
import com.yumo.demo.view.YmTestView;
import com.yumo.ymtest.base.BaseActivity;
import com.yumo.ymtest.dialog.TestViewDialog;

public class MainActivity extends BaseActivity{

    protected Toolbar mToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        YmTestView testView = findViewById(R.id.test_view);
        testView.init(this, getClass());

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
    }

    /**
     * 分包名进行demo测试。
     */
    @YmMethodTest(name = "打开所有的YmTestActivity")
    public void testYmActivity(){
       YmUIDemoManager.getInstance().openTestActivity(getApplicationContext());
    }

    /**
     * 测试YmTestView类的使用
     */
    @YmMethodTest(name = "测试YmTestView的使用")
    public void openYmTestView(){
        TestViewDialog fragment = new TestViewDialog();
        fragment.show(getSupportFragmentManager(), "test_view");
    }

    @YmMethodTest(name = "打开测试首页包名")
    public void openPackageHomePage() {
        YmUIDemoManager.getInstance().openUiTestPackagePage(this, R.id.test_view, "com.yumo.ymtest", false);
    }

    @YmMethodTest(name = "打开全部测试类")
    public void openAllTestPage() {
        YmUIDemoManager.getInstance().openUiAllTestPage(this, R.id.test_view, "com.yumo.ymtest", false);
    }
}
