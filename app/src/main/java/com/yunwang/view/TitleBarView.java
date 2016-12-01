package com.yunwang.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunwang.R;

/**
 * Created by Administrator on 2016/8/14.
 * 初始化Title
 */
public class TitleBarView extends RelativeLayout implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvMore;
    private LinearLayout llBack;

    private Activity mActivity;
    private ImageView ivRight;
    private LinearLayout llAdd;
    private ImageView ivBack;//返回按钮

    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init((Activity) context);
    }

    /**
     * 初始化
     *
     * @param mActivity
     */
    private void init(Activity mActivity) {
        this.mActivity = mActivity;
        //获取根view
        View rootView = LayoutInflater.from(mActivity).inflate(R.layout.layout_title_bar, this);
        //获取title
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        //获取返回的LinearLayout
        llBack = (LinearLayout) rootView.findViewById(R.id.ll_back);
        //获取更多
        tvMore = (TextView) rootView.findViewById(R.id.tv_more);
        tvMore.setOnClickListener(this);
        llBack.setOnClickListener(this);
        //右边的图标
        ivRight = (ImageView) rootView.findViewById(R.id.iv_right);
        ivBack = (ImageView) rootView.findViewById(R.id.iv_back);
        //右边的根view
        llAdd = (LinearLayout) rootView.findViewById(R.id.ll_add);
        llAdd.setOnClickListener(this);
    }

    /**
     * 设置头部的信息
     *
     * @param text
     */
    public void setTitleText(int text) {
        if (0 == text) {
            return;
        }
        tvTitle.setText(this.getResources().getString(text));
    }

    /**
     * 设置头部的信息
     *
     * @param text
     */
    public void setTitleText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        tvTitle.setText(text);
    }

    /**
     * 设置返回view的显示状态
     */
    public void setBackVisibility() {
        llBack.setVisibility(GONE);
    }

    /**
     * 设置更多view的显示状态
     */
    public void setTvMoreVisibilityGone(int imageId) {
        tvMore.setVisibility(GONE);
        ivRight.setVisibility(VISIBLE);
        ivRight.setImageResource(imageId);
    }

    public void setAllViewGone() {
        ivBack.setVisibility(VISIBLE);
        ivBack.setImageResource(R.mipmap.icon_back);
        tvMore.setVisibility(GONE);
        ivRight.setVisibility(GONE);
    }

    /**
     * 设置右边的文字
     *
     * @param text
     */
    public void setRightText(String text) {
        ivRight.setVisibility(GONE);
        tvMore.setVisibility(VISIBLE);
        tvMore.setText(text);
    }

    /**
     * 设置右边文字是否可以被点击
     *
     * @param isClick
     */
    public void setRightTextClick(boolean isClick) {
        tvMore.setVisibility(VISIBLE);
        ivRight.setVisibility(GONE);
        if (isClick) {
            tvMore.setTextColor(getResources().getColor(R.color.color_white));
        } else {
            tvMore.setTextColor(getResources().getColor(R.color.color_dialog_bg));
        }
        tvMore.setClickable(isClick);
        tvMore.setEnabled(isClick);
        tvMore.setFocusable(isClick);
        llAdd.setClickable(isClick);
        llAdd.setEnabled(isClick);
        tvMore.setFocusable(isClick);
    }

    //获取右边的控件
    public View getAddWidget() {
        return ivRight;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                    mActivity.finish();
                    mActivity.overridePendingTransition(R.anim.in_form_left_back, R.anim.out_of_right_back);
                break;
            case R.id.ll_add:
            case R.id.tv_more:
                if (null != listener) {
                    //回调监听
                    listener.onRightClick();
                }
                break;
        }
    }

    private MyOnClickListener listener;

    //设置右边view的监听
    public void setOnRightListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    /**
     * 定义返回的接口
     */
    public interface MyOnClickListener {
        void onRightClick();
    }
}
