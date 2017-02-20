package com.yumo.ymtest.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.yumo.demo.view.YmTestView;
import com.yumo.ymtest.R;

/**
 * Created by YmModev on 17/2/16.
 */

public abstract class BaseDialogFragment extends DialogFragment{

    protected Toolbar mToolbar = null;
    protected YmTestView mTestView = null;
    protected FrameLayout mContainerView = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.common_dialog_full_style);
        dialog.setContentView(R.layout.base_dialog_fragment);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        mContainerView = (FrameLayout) dialog.findViewById(R.id.container_view);
        mContainerView.addView(getContentView());
        initView(dialog);
        return dialog;
    }

    protected void initView(Dialog dialog){
        mToolbar = (Toolbar)dialog.findViewById(R.id.toolbar);
        mTestView = (YmTestView)dialog.findViewById(R.id.test_view);
    }

    protected abstract View getContentView();
}
