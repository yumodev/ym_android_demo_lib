package com.yumo.demo.customui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;



import com.yumo.demo.R;
import com.yumo.demo.config.YmTestDefine;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class YmTestListDialog extends DialogFragment {

    private List<String> mDataList = new ArrayList<>();
    private RecyclerView mListView = null;
    private CommonAdapter<String> mAdapter = null;

    public YmTestListDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(R.style.ymtest_full_dialog, 0);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(YmTestDefine.KEY_STR_LIST)){
            mDataList = bundle.getStringArrayList(YmTestDefine.KEY_STR_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ymtest_list_dialog, null);
        initView(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.horizontalMargin = 0f;
        lp.verticalMargin = 0f;
        getDialog().getWindow().setAttributes(lp);
        getDialog().getWindow().getDecorView().setPadding(0,0,0,0);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    private void initView(View root) {
        mListView = root.findViewById(R.id.list);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.ymtest_linear_text_item, mDataList) {
            @Override
            protected void convert(ViewHolder holder, String data, int position) {
                holder.setText(R.id.content, data);
            }
        };

        mListView.setAdapter(mAdapter);
    }
}
