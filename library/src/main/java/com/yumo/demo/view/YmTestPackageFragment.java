package com.yumo.demo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yumo.demo.R;
import com.yumo.demo.base.YmDemoBaseFragment;
import com.yumo.demo.entry.YmPackageInfo;
import com.yumo.demo.listener.IGetPackageData;
import com.yumo.demo.utils.ClassUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yumodev on 15/11/20.
 */
public class YmTestPackageFragment extends YmDemoBaseFragment {
    private final String LOG_TAG = "YmTestPackageFragment";

    private RecyclerView mListView = null;
    private List<YmPackageInfo> mDataList = null;

    @Override
    protected View getContainerView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mToolbar.setTitle("DemoTest");
        mToolbar.setNavigationIcon(R.drawable.head_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        initData();

        CommonAdapter<YmPackageInfo> adapter = new CommonAdapter<YmPackageInfo>(getContext(), R.layout.linearlayout_text_item, mDataList) {
            @Override
            protected void convert(ViewHolder holder, YmPackageInfo testPackageInfo, int position) {
                holder.setText(R.id.content, testPackageInfo.mTitle);
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                YmPackageInfo data = mDataList.get(position);
                String packageName = data.mPackageName;
                Bundle bundle = new Bundle();
                bundle.putString("packageName", packageName);
                YmTestClassFragment classFragment = new YmTestClassFragment();
                classFragment.setArguments(bundle);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.test_fragment_id, classFragment).commit();
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
    private void initData(){
        String subClassName = ClassUtils.getFirstSubClassName(getContext(), IGetPackageData.class);
        if (TextUtils.isEmpty(subClassName)){
            return;
        }

        try{
            Class<?> cls = Class.forName(subClassName);
            Method getPackageList = cls.getMethod("getPackageList", new Class<?>[] {});
            mDataList = (List<YmPackageInfo>)getPackageList.invoke(cls.newInstance());
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
