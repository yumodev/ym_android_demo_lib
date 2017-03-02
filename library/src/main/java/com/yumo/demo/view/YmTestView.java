package com.yumo.demo.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yumo.demo.R;
import com.yumo.demo.config.Config;
import com.yumo.demo.entry.YmMethod;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 17/2/15.
 */

public class YmTestView extends FrameLayout {
    private Object mObj = null;
    private Class<?> mCls = null;
    private List<YmMethod> mMethodList = new ArrayList<>();
    private RecyclerView mListView = null;

    public YmTestView(Context context) {
        super(context);
    }

    public YmTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YmTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Object obj, Class<?> cls, View headerView, View footerView){
        mObj = obj;
        mCls = cls;

        mMethodList = getMethodListData(cls);

        final CommonAdapter<YmMethod> adapter = new CommonAdapter<YmMethod>(getContext(), R.layout.linearlayout_text_item, mMethodList) {
            @Override
            protected void convert(ViewHolder holder, YmMethod ymMethod, int position) {
                if (TextUtils.isEmpty(ymMethod.getDisplayName())) {
                    holder.setText(R.id.content, ymMethod.getMethod().getName());
                } else {
                    {
                        holder.setText(R.id.content, ymMethod.getDisplayName());
                    }
                }
            }
        };

        final HeaderAndFooterWrapper headerAndFooterWrapper= new HeaderAndFooterWrapper<>(adapter);
        if (headerView != null){
            if (headerView.getLayoutParams() == null){
                headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            headerAndFooterWrapper.addHeaderView(headerView);
        }

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                try {
                    mMethodList.get(position-headerAndFooterWrapper.getHeadersCount()).getMethod().invoke(mObj, (Object[]) null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        if (footerView != null){
            if (footerView.getLayoutParams() == null){
                footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            headerAndFooterWrapper.addFootView(footerView);
        }

        mListView = new RecyclerView(getContext());
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.setAdapter(headerAndFooterWrapper);
        addView(mListView);
    }

    public void init(Object obj, Class<?> cls){
        init(obj, cls, null, null);
    }

    public boolean addHeaderView(View view){
        if (mListView == null){
            return false;
        }

        if (view.getLayoutParams() == null){
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        HeaderAndFooterWrapper adapter = HeaderAndFooterWrapper.class.cast(mListView.getAdapter());
        adapter.addHeaderView(view);
        return true;
    }

    public boolean addFooterView(View view){
        if (mListView == null){
            return false;
        }

        if (view.getLayoutParams() == null){
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        HeaderAndFooterWrapper adapter = HeaderAndFooterWrapper.class.cast(mListView.getAdapter());
        adapter.addFootView(view);
        return true;
    }

    private List<YmMethod> getMethodListData(Class<?> c){
        List<YmMethod> list = new ArrayList<>();
        Method[] m = c.getDeclaredMethods();

        for (int i = 0; i < m.length; i++) {
            if (m[i].getName().indexOf(Config.TEST_METHOD_PREFIX) == 0) {
                YmMethod ymMethod = new YmMethod();
                ymMethod.setMethod(m[i]);
                list.add(ymMethod);
            }
        }

        return list;
    }
}
