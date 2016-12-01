package com.yunwang.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.utils.LaunchActivityUtils;
import com.yunwang.view.TitleBarView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/11/23.
 */
@ContentView(R.layout.activity_pda)
public class PDAActivity extends BaseActivty implements TitleBarView.MyOnClickListener, View.OnClickListener {

    @ViewInject(R.id.ll_root_view)
    private LinearLayout rootView;//根部的view

    @ViewInject(R.id.iv_facade_check)
    private ImageView ivFacadeCheck;//外观检查

    @ViewInject(R.id.iv_chassis_dynamic)
    private ImageView ivChassisDynamic;//底盘动态

    @ViewInject(R.id.iv_other_infomation)
    private ImageView ivOtherInfomation;//其他信息录入

    @Override
    protected void initView() {
        rootView.addView(titleBarView, 0);
        titleBarView.setTitleText(R.string.tv_check_project_selected);
        titleBarView.setRightText(getResources().getString(R.string.tv_detail_infomation));
        titleBarView.setOnRightListener(this);

        //进行监听
        ivFacadeCheck.setOnClickListener(this);
        ivChassisDynamic.setOnClickListener(this);
        ivOtherInfomation.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    /**
     * 点击右边文字进行跳转
     */
    @Override
    public void onRightClick() {
        //跳转到车辆的详细信息界面
        startActivity(mActivity,CarInfomationActivity.class,null,0,false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_facade_check:
                LaunchActivityUtils.startActivity(PDAActivity.this, OutsidePhotoActivity.class, null);
                break;
            case R.id.iv_chassis_dynamic:
                LaunchActivityUtils.startActivity(PDAActivity.this,ChassisActivity.class,null);
                break;
            case R.id.iv_other_infomation:
                LaunchActivityUtils.startActivity(PDAActivity.this, OtherMessageActivity.class, null);
                break;
        }
    }
}
