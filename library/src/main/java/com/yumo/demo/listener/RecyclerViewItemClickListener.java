package com.yumo.demo.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yumodev on 17/2/15.
 */

public interface RecyclerViewItemClickListener {

    void onItemClick(RecyclerView.Adapter adapter, View v, int position);
}
