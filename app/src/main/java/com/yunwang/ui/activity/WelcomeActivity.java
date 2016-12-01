package com.yunwang.ui.activity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yunwang.R;
import com.yunwang.base.BaseActivty;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/11/21.
 */

@ContentView(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivty
        implements Animation.AnimationListener {

    @ViewInject(R.id.iv_welcome)
    private ImageView ivWelcome;

    @Override
    protected void initView() {
        //欢迎页禁止滑动删除
        setSwipeBackEnable(false);
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.anim_welcome_scale);
        //设置动画监听
        animation.setAnimationListener(this);
        //fillAfter的值为true,则动画执行后，控件将停留在执行结束的状态
        animation.setFillAfter(true);
        //开启动画
        ivWelcome.startAnimation(animation);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity(this, LoginActivity.class, null, 0, false);
        WelcomeActivity.this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
