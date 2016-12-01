package com.yunwang.ui.activity;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.manager.PreferenceManager;
import com.yunwang.utils.Constants;
import com.yunwang.view.TitleBarView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/11/21.
 */
@ContentView(R.layout.activity_set_url)
public class SetUrlActivity extends BaseActivty implements TitleBarView.MyOnClickListener {

    @ViewInject(R.id.ll_root_view)
    private LinearLayout rootView;//根部的view

    @ViewInject(R.id.et_url)
    private EditText etUrl;//url地址

    @ViewInject(R.id.et_line_number)
    private EditText etLineNumber;//线号

    @Override
    protected void initView() {
        rootView.addView(titleBarView,0);
        titleBarView.setTitleText(R.string.tv_setting);
        titleBarView.setRightText(getResources().getString(R.string.tv_ensure));
        titleBarView.setOnRightListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onRightClick() {
        if(!TextUtils.isEmpty(etUrl.getText().toString().trim())){
            //保存url到本地
            PreferenceManager.setString(mActivity, Constants.URL,etUrl.getText().toString());
        }
        if(!TextUtils.isEmpty(etLineNumber.getText().toString().trim())){
            //保存线号到本地
            PreferenceManager.setString(mActivity, Constants.URL,etLineNumber.getText().toString());
        }
        //结束当前的界面
        finishActivity();
    }
}
