package com.yumo.ymtest.web;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/2/16.
 * 测试WebView的使用
 */

public class WebViewTestFragment extends YmTestFragment {

    public void testWebView(){
        WebView wv = new WebView(getContext());
        wv.loadUrl("http://m.baidu.com");
        wv.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        showTestView(wv);
    }
}
