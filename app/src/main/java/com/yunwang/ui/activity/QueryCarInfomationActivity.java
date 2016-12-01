package com.yunwang.ui.activity;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.manager.ToastManager;
import com.yunwang.view.TitleBarView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/11/22.
 */

@ContentView(R.layout.activity_query_car_information)
public class QueryCarInfomationActivity extends BaseActivty implements TitleBarView.MyOnClickListener {

    @ViewInject(R.id.ll_root_view)
    private LinearLayout rootView;//根部的view

    @ViewInject(R.id.et_complete_car_number)
    private EditText etCompleteCarNumber;//完整车牌号

    @ViewInject(R.id.et_car_number)
    private EditText etCarNumber;//车架号后四位

    @Override
    protected void initView() {
        rootView.addView(titleBarView, 0);
        titleBarView.setTitleText(R.string.tv_query_car_information);
        titleBarView.setRightText(getResources().getString(R.string.tv_query_information));
        titleBarView.setOnRightListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onRightClick() {
        chcekMsg();
    }

    /**
     * 校验信息
     */
    private void chcekMsg() {
        if (TextUtils.isEmpty(etCompleteCarNumber.getText().toString().trim())) {
            ToastManager.createToast(mActivity).showToast(R.string.tv_please_complete_car_number, ToastManager.GRAVITY_CENTER);
            return;
        }
        if (TextUtils.isEmpty(etCarNumber.getText().toString().trim())) {
            ToastManager.createToast(mActivity).showToast(R.string.tv_please_input_car_number, ToastManager.GRAVITY_CENTER);
            return;
        }
        queryCarInformation();
    }

    /**
     * 获取查询信息
     */
    private void queryCarInformation() {

    }
}
