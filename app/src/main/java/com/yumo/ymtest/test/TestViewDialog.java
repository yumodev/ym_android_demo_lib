package com.yumo.ymtest.test;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yumo.ymtest.R;
import com.yumo.ymtest.base.BaseDialogFragment;

/**
 * Created by yumodev on 17/2/15.
 *
 * 提供YmTestView的测试使用方式
 */

public class TestViewDialog extends BaseDialogFragment {

    private EditText mFirstNumEdit = null;
    private EditText mSecondNumEdit = null;
    private TextView mResultTextView = null;

    public TestViewDialog() {
        super();
    }

    @Override
    protected void initView(Dialog dialog){
        super.initView(dialog);
        mFirstNumEdit = dialog.findViewById(R.id.first_num_edit);
        mSecondNumEdit = dialog.findViewById(R.id.second_num_edit);
        mResultTextView = dialog.findViewById(R.id.result_num);
        mTestView.init(this, getClass());

        mToolbar.setTitle("TestView");
        mToolbar.setNavigationIcon(R.drawable.head_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    protected View getContentView() {
        return View.inflate(getContext(),R.layout.test_view_dialog, null);
    }

    private int getFirstNum(){
        String num = mFirstNumEdit.getText().toString();
        if (TextUtils.isEmpty(num)){
            return 0;
        }else{
            return Integer.parseInt(num);
        }
    }

    private int getSecondNum(){
        String num = mSecondNumEdit.getText().toString();
        if (TextUtils.isEmpty(num)){
            return 0;
        }else{
            return Integer.parseInt(num);
        }
    }

    public void testAdd(){
        int num1 = getFirstNum();
        int num2 = getSecondNum();
        int sum = num1 + num2;
        mResultTextView.setText(String.valueOf(sum));
    }

    public void testSubtract(){
        int num1 = getFirstNum();
        int num2 = getSecondNum();
        int sum = num1 - num2;
        mResultTextView.setText(String.valueOf(sum));
    }

    public void testMultiplication(){
        int num1 = getFirstNum();
        int num2 = getSecondNum();
        int sum = num1 * num2;
        mResultTextView.setText(String.valueOf(sum));
    }

    public void testDivide(){
        int num1 = getFirstNum();
        int num2 = getSecondNum();
        if (num2 != 0){
            int sum = num1 / num2;
            mResultTextView.setText(String.valueOf(sum));
        }else{
            Toast.makeText(getContext(), "除数不能为0", Toast.LENGTH_SHORT).show();
        }
    }
}
