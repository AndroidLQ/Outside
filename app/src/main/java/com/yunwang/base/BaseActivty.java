package com.yunwang.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.yunwang.R;
import com.yunwang.manager.AppManager;
import com.yunwang.utils.LaunchActivityUtils;
import com.yunwang.view.CustomProgressDialog;
import com.yunwang.view.SystemBarTintManager;
import com.yunwang.view.TitleBarView;

import org.xutils.x;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Administrator on 2016/11/17.
 */

//所有的Activity基类(继承SwipeBackActivity进行侧滑删除)
    //写成抽象类
public abstract class BaseActivty extends SwipeBackActivity {

    protected SwipeBackLayout mSwipeBackLayout;//滑动删除布局实例

    //滑动的边距
    private static final int EDGE_DISTANCE = 480;

    //全局的上下文
    protected BaseActivty mActivity;

    //TitleBar
    protected TitleBarView titleBarView;

    //进度条Dialog
    protected CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //没有标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //为所有的Activity设置为竖屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mActivity = this;
        //获取滑动删除的布局实例
        mSwipeBackLayout = this.getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);//设置左滑结束
        //设置滑动边缘
        mSwipeBackLayout.setEdgeSize(EDGE_DISTANCE);
        mSwipeBackLayout.setScrimColor(Color.TRANSPARENT);
        // 进行注解
        //加载布局文件
        x.view().inject(this);
        //设定状态栏的颜色
        setTranslucentStatus();
        //获取titleBar
        getAllHeaderView();
        //创建进度条
        dialog = CustomProgressDialog.createDialog(this, "正在加载中...", true, true);
        //将Activity加入栈中
        AppManager.getAppManager().addActivity(this);
        //初始化view
        initView();
        //加载数据
        loadData();
    }

    private void setTranslucentStatus() {
        // 设定状态栏的颜色，当版本大于4.4时起作用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(mActivity, true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            // 此处可以重新指定状态栏颜色
            tintManager.setStatusBarTintResource(R.color.tab_main_bg);
            Log.i("TAG","HELLO GIT");
//            tintManager.setNavigationBarTintEnabled(true);

            // 自定义颜色
//            tintManager.setTintColor(R.color.focus_circle_bg);
        }

//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                Window window = this.getWindow();
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(this.getResources().getColor(R.color.tab_main_bg));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 获取titleBar
     */
    private View getAllHeaderView() {
        titleBarView = (TitleBarView) LayoutInflater.from(mActivity).inflate(R.layout.layout_all_header_view, null);
        return titleBarView;
    }

    /**
     * 开启一个Activity
     *
     * @param from        从哪个Activity进行跳转
     * @param to          跳转到哪个Activity
     * @param intent      意图
     * @param requestCode 请求码
     * @param hasResult   是否需要返回的结果(true表示需要返回的结果)
     */
    protected void startActivity(Activity from, Class<?> to, Intent intent, int requestCode, boolean hasResult) {
        if (hasResult) {
            LaunchActivityUtils.startActivityForResult(from, to, intent, requestCode);
        } else {
            LaunchActivityUtils.startActivity(from, to, intent);
        }
    }

    /**
     * 结束Activity
     */
    protected void finishActivity() {
        this.finish();
        this.overridePendingTransition(R.anim.in_form_left_back, R.anim.out_of_right_back);
    }

    //初始化view
    protected abstract void initView();

    //加载数据
    protected abstract void loadData();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishActivity();
        }
        return super.onKeyDown(keyCode, event);
    }

    //进行推送统计，需要在所有的Activity里面进行编写代码
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
