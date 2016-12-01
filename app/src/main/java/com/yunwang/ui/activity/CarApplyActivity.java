package com.yunwang.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.utils.Util;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;
import java.util.Date;

import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;

@ContentView(R.layout.activity_car_apply)
public class CarApplyActivity extends BaseActivty implements OnAddressSelectedListener, View.OnClickListener {

    @ViewInject(R.id.activity_car_apply)
    LinearLayout title_ll;

    @ViewInject(R.id.eveybody_layout)
    View everybody_layout_view;

    @ViewInject(R.id.agent_layout)
    View agent_layout_view;

    @ViewInject(R.id.type_layout)
    View type_layout_view;

    private TextView area_tv;
    private LinearLayout everybody_layout_area_ll;
    private LinearLayout apply_date_ll;
    private TextView apply_date_tv;

    private TimePickerView pvTime;


    @Override
    protected void initView() {
        title_ll.addView(titleBarView, 0);
        titleBarView.setTitleText("申请信息填写");
        titleBarView.setAllViewGone();

        everybody_layout_area_ll = (LinearLayout) everybody_layout_view.findViewById(R.id.everybody_layout_area_ll);
        everybody_layout_area_ll.setOnClickListener(this);
        area_tv = (TextView) everybody_layout_area_ll.findViewById(R.id.car_apply_area);
        apply_date_ll = (LinearLayout) type_layout_view.findViewById(R.id.apply_date_ll);
        apply_date_ll.setOnClickListener(this);
        apply_date_tv = (TextView) apply_date_ll.findViewById(R.id.apply_date_tv);

        initDateSelectDialog();
    }

    private void initDateSelectDialog() {
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 5, calendar.get(Calendar.YEAR) + 15);//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                apply_date_tv.setText(Util.getTime(date));
            }
        });
    }

    @Override
    protected void loadData() {


    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissAddressSelectDialog();
        dismissDateSelectDialog();
    }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        String s = (province == null ? "" : province.name) +
                (city == null ? "" : city.name) +
                (county == null ? "" : county.name) +
                (street == null ? "" : street.name);
        area_tv.setText(s);
        dismissAddressSelectDialog();

    }

    private void dismissAddressSelectDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void dismissDateSelectDialog() {
        if (pvTime != null && pvTime.isShowing())
            pvTime.dismiss();
    }

    BottomDialog dialog;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.everybody_layout_area_ll:
                dialog = new BottomDialog(mActivity);
                dialog.setOnAddressSelectedListener(this);
                dialog.show();
                break;
            case R.id.apply_date_ll:
                pvTime.show();
                break;
        }
    }
}
