package com.yunwang.ui.activity;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.model.CarNumber;
import com.yunwang.ui.adapter.CarNumberAdapter;
import com.yunwang.ui.adapter.CarSelectAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */
@ContentView(R.layout.activity_query_car_history)
public class QueryCarHistoryActivity extends BaseActivty implements TextWatcher, LRecyclerView.LScrollListener, OnItemClickListener, View.OnClickListener {

    @ViewInject(R.id.ll_root_view)
    private LinearLayout rootView;//根部的view

    @ViewInject(R.id.tv_car_type)
    private TextView tvCarType;//车的类型

    @ViewInject(R.id.et_search)
    private EditText etSearch;//搜索

    @ViewInject(R.id.recycler_view)
    private LRecyclerView mRecyclerView;//RecyclerView

    private ArrayList<CarNumber> datas;//数据源

    private CarNumberAdapter adapter;//车牌号的适配器

    @Override
    protected void initView() {
        rootView.addView(titleBarView, 0);
        titleBarView.setTitleText(R.string.tv_car_check_complete);
        titleBarView.setAllViewGone();

        //搜索进行监听
        etSearch.addTextChangedListener(this);
        tvCarType.setOnClickListener(this);

        //设置布局方式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        datas = new ArrayList<>();
        adapter = new CarNumberAdapter(mActivity, datas);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(lRecyclerViewAdapter);
        //允许下拉刷新
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLScrollListener(this);
        //进行监听
        lRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onScrollUp() {

    }

    @Override
    public void onScrollDown() {

    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {

    }

    @Override
    public void onScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_car_type:
                showPopupWindow();
                break;
        }
    }

    /**
     * 显示选择车辆的窗口
     */
    private void showPopupWindow() {
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.layout_select_car_window, null);


        LRecyclerView mRecyclerView = (LRecyclerView) contentView.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        //设置布局方式
        mRecyclerView.setLayoutManager(manager);
        //数据源
        String[] datas = getResources().getStringArray(R.array.tv_car_type);
        //转换为集合
        final ArrayList<String> results = new ArrayList<>(Arrays.asList(datas));

        CarSelectAdapter adapter = new CarSelectAdapter(mActivity, results);
        LRecyclerViewAdapter mAdapter = new LRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(mAdapter);

        //禁止下拉刷新
        mRecyclerView.setPullRefreshEnabled(false);
        final PopupWindow popupWindow = new PopupWindow(contentView, 300,
                1000, true);
        //设置点击可以消失
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(tvCarType, 0, 0);

        //设置item监听
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //获取到具体的车的类型
                String carType = results.get(position);
                tvCarType.setText(carType);
                if (null != popupWindow && popupWindow.isShowing()) {
                    //关闭PopupWindow
                    popupWindow.dismiss();
                }
                //TODO 进行网络请求
                requestData(carType);
            }
        });
    }

    /**
     * 进行网络请求
     *
     * @param carType 车的类型
     */
    private void requestData(String carType) {

    }

}
