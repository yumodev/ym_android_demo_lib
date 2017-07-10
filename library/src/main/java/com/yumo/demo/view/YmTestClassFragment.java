package com.yumo.demo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yumo.demo.R;
import com.yumo.demo.base.YmDemoBaseFragment;
import com.yumo.demo.utils.YmClassUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 15/11/20.
 */
public class YmTestClassFragment extends YmDemoBaseFragment {
    private final String LOG_TAG = "YmTestClassFragment";

    private RecyclerView mListView = null;
    private List<Class<?>> mDataList = null;
    private String mPackageName = "";

    @Override
    protected View getContainerView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null){
            mPackageName = getArguments().getString("packageName");
        }

        mToolbar.setTitle(mPackageName);
        mToolbar.setNavigationIcon(R.drawable.head_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        initData();

        CommonAdapter<Class<?>> adapter = new CommonAdapter<Class<?>>(getContext(), R.layout.linearlayout_text_item, mDataList) {
            @Override
            protected void convert(ViewHolder holder, final Class<?> cls, int position) {
                holder.setText(R.id.content, cls.getSimpleName());
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Class<?> cls = mDataList.get(position);
                if (Activity.class.isAssignableFrom(cls)){
                    getActivity().startActivity(new Intent(getActivity(), cls));
                }else{
                    YmTestFragment fragment = (YmTestFragment) Fragment.instantiate(getContext(), cls.getName());

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.test_fragment_id, fragment).commit();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mListView = new RecyclerView(getContext());
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.setAdapter(adapter);
        return mListView;
    }

    /**
     * yumodev
     * void
     * 2014-11-6
     */
    private void initData() {
        List<Class<?>> superClassList = new ArrayList<>();
        superClassList.add(YmTestFragment.class);
        superClassList.add(Activity.class);

        mDataList = YmClassUtils.getAllSubClassInPackage(getContext(), superClassList, mPackageName);
    }

}
