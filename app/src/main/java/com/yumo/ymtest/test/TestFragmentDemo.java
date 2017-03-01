package com.yumo.ymtest.test;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yumo.demo.view.YmTestFragment;
import com.yumo.ymtest.R;

/**
 * Created by yumodev on 17/2/28.
 *
 * 测试HeaderView的使用。
 */

public class TestFragmentDemo extends YmTestFragment {

    private EditText mFirstNumEdit = null;
    private EditText mSecondNumEdit = null;
    private TextView mResultTextView = null;

    public TestFragmentDemo() {
        super();
    }

    @Override
    public View getHeaderView() {
        View view = View.inflate(getContext(),R.layout.test_view_dialog, null);
        mFirstNumEdit = (EditText) view.findViewById(R.id.first_num_edit);
        mSecondNumEdit = (EditText) view.findViewById(R.id.second_num_edit);
        mResultTextView = (TextView) view.findViewById(R.id.result_num);
        return view;
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
