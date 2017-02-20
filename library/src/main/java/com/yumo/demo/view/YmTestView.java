package com.yumo.demo.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yumo.demo.R;
import com.yumo.demo.config.Config;
import com.yumo.demo.entry.YmMethod;
import com.yumo.demo.listener.RecyclerViewItemClickListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 17/2/15.
 */

public class YmTestView extends FrameLayout {
    private final String LOG_TAG = "MethodRecyclerView";

    private MethodAdapter mAdapter = null;
    private Object mObj = null;
    private Class<?> mCls = null;
    private List<YmMethod> mMethodList = new ArrayList<>();
    private RecyclerView mRecyclerView = null;

    public YmTestView(Context context) {
        super(context);
    }

    public YmTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YmTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Object obj, Class<?> cls){
        mObj = obj;
        mCls = cls;
        mRecyclerView = new RecyclerView(getContext());
        mMethodList = getMethodListData(cls);
        mAdapter = new MethodAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {
                try {
                    mAdapter.getItemData(position).getMethod().invoke(mObj, (Object[]) null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        addView(mRecyclerView);
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

    public class MethodAdapter extends RecyclerView.Adapter<MethodViewHolder> {

        private Context mContext = null;
        private RecyclerViewItemClickListener mItemClickListener = null;
        public MethodAdapter(Context context){
            mContext = context;
        }
        @Override
        public MethodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MethodViewHolder(View.inflate(mContext, R.layout.linearlayout_text_item, null));
        }

        @Override
        public void onBindViewHolder(final MethodViewHolder holder, int position) {
            holder.mMethodNameView.setText(getItemData(position).getMethod().getName());
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(MethodAdapter.this, v, holder.getAdapterPosition());
                }
            });
        }

        public YmMethod getItemData(int position){
            return mMethodList.get(position);
        }

        @Override
        public int getItemCount() {
            return mMethodList.size();
        }

        public void setItemClickListener(RecyclerViewItemClickListener listener){
            mItemClickListener = listener;
        }
    }

    static class MethodViewHolder extends RecyclerView.ViewHolder{
        public TextView mMethodNameView = null;
        public MethodViewHolder(View itemView) {
            super(itemView);
            mMethodNameView = (TextView)itemView.findViewById(R.id.content);
        }
    }
}
