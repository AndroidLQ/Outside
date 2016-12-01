package com.yunwang.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunwang.view.CustomProgressDialog;

import org.xutils.x;

/**
 * Created by Administrator on 2016/6/20.
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment {

    //单例模式NewFragment()、
    // 不能使用默认构造器创建对象、
    // 外部只能下面的来创建、创建一个fragment类型、通过传入类型id产生多个不同的fragment实例对象
 /*   public static NewFragment newInastance(int category_id) {
        NewFragment nf = new NewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category_id", category_id);
        nf.setArguments(bundle);//绑定数据最早执行
        return nf;
    }*/

    //上下文
    protected Activity mActivity;
    //是否加载布局
    private boolean injected = false;

    //进度条
    protected CustomProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        //基类view
        //调用x.view().inject()方法返回view
        View rootView = x.view().inject(this, inflater, container);
        return rootView;
    }

    //onCreateView是创建的时候调用,onViewCreated是在onCreateView后被触发的事件
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //创建进度条
        dialog = CustomProgressDialog.createDialog(mActivity, "正在加载中...", true, true);
        //初始化view
        initView();
        //加载数据
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //初始化view
    protected abstract void initView();

    //加载数据
    protected abstract void loadData();
}
