package com.yunwang.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.model.CarNumber;
import com.yunwang.ui.adapter.CarNumberAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivty implements
        OnItemClickListener, LRecyclerView.LScrollListener, View.OnClickListener, TextWatcher {
    private  String TAG = this.getClass().getSimpleName().toString();

    @ViewInject(R.id.ll_root_view)
    private LinearLayout rootView;

    @ViewInject(R.id.recycler_view)
    private LRecyclerView mRecyclerView;

    @ViewInject(R.id.tv_query_car)
    private TextView tvQueryCar;//车辆信息查询

    @ViewInject(R.id.tv_query_histor_car)
    private TextView tvQueryHistorCar;//历史车辆查询

    @ViewInject(R.id.et_car_number)
    private EditText etCarNumber;//车牌号码

    private ArrayList<CarNumber> datas;//数据源

    private CarNumberAdapter adapter;//车牌号的适配器

    //pda外观检测的key值
    public static final String PAD_KEY = "pda_key";

    @Override
    protected void initView() {
        rootView.addView(titleBarView, 0);
        titleBarView.setTitleText(R.string.tv_select_car_number);
        titleBarView.setAllViewGone();

        tvQueryCar.setOnClickListener(this);
        tvQueryHistorCar.setOnClickListener(this);

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

        //对输入框进行监听
        etCarNumber.addTextChangedListener(this);

        //TODO 先填入一些死数据
        initData();
    }

    private void initData() {
        for (int i = 0, count = 10; i < count; i++) {
            CarNumber carNumber = new CarNumber();
            carNumber.carType = "小汽车" + i;
            carNumber.carCheckStatus = "复检" + i;
            carNumber.carNumber = "09WA5" + i;
            datas.add(carNumber);
        }
        //通知适配器发生改变
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra(PAD_KEY, datas.get(position));
        //跳转到外观检测页面
        startActivity(mActivity, PDAActivity.class, intent, 0, false);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_query_car:
                startActivity(mActivity, QueryCarInfomationActivity.class, null, 0, false);
                break;
            case R.id.tv_query_histor_car:
                startActivity(mActivity, QueryCarHistoryActivity.class, null, 0, false);
                break;
        }
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
}
