package com.yunwang.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.interfaces.SelectItemInterface;
import com.yunwang.model.SelectCommonModel;
import com.yunwang.model.SelectItemModel;
import com.yunwang.utils.AnaysisUtil;
import com.yunwang.view.AddItemPopupWindows;
import com.yunwang.view.TitleBarView;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_select_check_item)
public class SelectCheckItemActivity extends BaseActivty implements View.OnClickListener,SelectItemInterface {
    private static String TAG = "SelectCheckItemActivity";
    private String json = "{\"motorbike\":[{\"type\":0,\"data\":[{\"title\":\"号牌号码/车辆类型\",\"tag\":false},{\"title\":\"车辆品牌/型号\",\"tag\":false},{\"title\":\"车辆识别代码\",\"tag\":false},{\"title\":\"发动机号码\",\"tag\":false},{\"title\":\"车辆颜色和外形\",\"tag\":false}]},{\"type\":1,\"data\":[{\"title\":\"外廓尺寸\",\"tag\":false},{\"title\":\"轴距\",\"tag\":false},{\"title\":\"整备质量\",\"tag\":false},{\"title\":\"核定载人数\",\"tag\":false},{\"title\":\"核定载质量\",\"tag\":false},{\"title\":\"栏板高度\",\"tag\":false},{\"title\":\"后轴钢板弹簧片数\",\"tag\":false},{\"title\":\"客车应急出口\",\"tag\":false},{\"title\":\"客车乘客通道和引道\",\"tag\":false},{\"title\":\"货箱\",\"tag\":false}]},{\"type\":2,\"data\":[{\"title\":\"外廓尺寸\",\"tag\":false},{\"title\":\"轴距\",\"tag\":false},{\"title\":\"整备质量\",\"tag\":false},{\"title\":\"核定载人数\",\"tag\":false},{\"title\":\"核定载质量\",\"tag\":false},{\"title\":\"栏板高度\",\"tag\":false},{\"title\":\"后轴钢板弹簧片数\",\"tag\":false},{\"title\":\"客车应急出口\",\"tag\":false},{\"title\":\"客车乘客通道和引道\",\"tag\":false},{\"title\":\"货箱\",\"tag\":false}]},{\"type\":3,\"data\":[{\"title\":\"外廓尺寸\",\"tag\":false},{\"title\":\"轴距\",\"tag\":false},{\"title\":\"整备质量\",\"tag\":false},{\"title\":\"核定载人数\",\"tag\":false},{\"title\":\"核定载质量\",\"tag\":false},{\"title\":\"栏板高度\",\"tag\":false},{\"title\":\"后轴钢板弹簧片数\",\"tag\":false},{\"title\":\"客车应急出口\",\"tag\":false},{\"title\":\"客车乘客通道和引道\",\"tag\":false},{\"title\":\"货箱\",\"tag\":false}]},{\"type\":4,\"data\":[{\"title\":\"外廓尺寸\",\"tag\":false},{\"title\":\"轴距\",\"tag\":false},{\"title\":\"整备质量\",\"tag\":false},{\"title\":\"核定载人数\",\"tag\":false},{\"title\":\"核定载质量\",\"tag\":false},{\"title\":\"栏板高度\",\"tag\":false},{\"title\":\"后轴钢板弹簧片数\",\"tag\":false},{\"title\":\"客车应急出口\",\"tag\":false},{\"title\":\"客车乘客通道和引道\",\"tag\":false},{\"title\":\"货箱\",\"tag\":false}]},{\"type\":5,\"data\":[{\"title\":\"外廓尺寸\",\"tag\":false},{\"title\":\"轴距\",\"tag\":true},{\"title\":\"整备质量\",\"tag\":false},{\"title\":\"核定载人数\",\"tag\":true},{\"title\":\"核定载质量\",\"tag\":false},{\"title\":\"栏板高度\",\"tag\":false},{\"title\":\"后轴钢板弹簧片数\",\"tag\":false},{\"title\":\"客车应急出口\",\"tag\":false},{\"title\":\"客车乘客通道和引道\",\"tag\":false},{\"title\":\"货箱\",\"tag\":true}]},{\"type\":6,\"data\":[{\"title\":\"外廓尺寸\",\"tag\":false},{\"title\":\"轴距\",\"tag\":true},{\"title\":\"整备质量\",\"tag\":false},{\"title\":\"核定载人数\",\"tag\":true},{\"title\":\"核定载质量\",\"tag\":false},{\"title\":\"栏板高度\",\"tag\":false},{\"title\":\"后轴钢板弹簧片数\",\"tag\":false},{\"title\":\"客车应急出口\",\"tag\":false},{\"title\":\"客车乘客通道和引道\",\"tag\":false},{\"title\":\"货箱\",\"tag\":true}]}]}";
    private String selectCar[] = new String[]{"非运营小型、微型载客汽车", "其他类型载客汽车", "载货汽车（三轮汽车除外）、专项作业车", "挂车", "三轮汽车", "摩托车"};

    @ViewInject(R.id.activity_select_check_item)
    LinearLayout title;

    @ViewInject(R.id.content)
    LinearLayout check_item;

    @ViewInject(R.id.car_select_ll)
    LinearLayout car_select_ll;

    View select_check_item_view;
    View common_check_item_view;
    TextView select_check_common_item_title;
    LinearLayout select_check_common_item;

    TextView select_check_item_title_tv;
    CheckBox select_check_item_cb;

    @Override
    protected void initView() {
        title.addView(titleBarView, 0);
        titleBarView.setTitleText("选择检查项目");
        titleBarView.setAllViewGone();
        titleBarView.setRightText("预览");
        titleBarView.setRightTextClick(true);
        titleBarView.setOnRightListener(new TitleBarView.MyOnClickListener() {
            @Override
            public void onRightClick() {
                Toast.makeText(mActivity, "点击了预览", Toast.LENGTH_LONG).show();
                //遍历所有view
                for (int i = 0; i < check_item.getChildCount(); i++) {
                    View child_view = check_item.getChildAt(i);
                    TextView title_tv = (TextView) child_view.findViewById(R.id.select_check_common_item_title);
                    Log.i(TAG, title_tv.getText().toString());

                }

            }
        });

        car_select_ll.setOnClickListener(this);


    }

    ArrayList<SelectCommonModel> content_List;

    @Override
    protected void loadData() {
        if (json != null) content_List = AnaysisUtil.getDataList(json);

        SelectCommonModel selectCommonModel = null;
        for (int i = 0; i < content_List.size(); i++) {
            common_check_item_view = LayoutInflater.from(mActivity).inflate(R.layout.common_check_item_view, null);
            select_check_common_item_title = (TextView) common_check_item_view.findViewById(R.id.select_check_common_item_title);
            select_check_common_item = (LinearLayout) common_check_item_view.findViewById(R.id.select_check_common_item);
            selectCommonModel = content_List.get(i);
            switch (selectCommonModel.getType()) {
                case 0:
                    select_check_common_item_title.setText("车辆唯一性检查");
                    break;
                case 1:
                    select_check_common_item_title.setText("车辆特征参数检查");
                    break;
                case 3:
                    select_check_common_item_title.setText("车辆外观检查");
                    break;
                case 4:
                    select_check_common_item_title.setText("安全装置检查");
                    break;
                case 5:
                    select_check_common_item_title.setText("底盘动态检验");
                    break;
                case 6:
                    select_check_common_item_title.setText("车辆底盘部件检查");
                    break;
            }
            for (int j = 0; j < selectCommonModel.getData().size(); j++) {
                select_check_item_view = LayoutInflater.from(mActivity).inflate(R.layout.select_check_item_view, null);
                select_check_item_title_tv = (TextView) select_check_item_view.findViewById(R.id.select_check_item_title);
                select_check_item_cb = (CheckBox) select_check_item_view.findViewById(R.id.select_check_item_cb);
                SelectItemModel selectItemModel = selectCommonModel.getData().get(j);
                select_check_item_title_tv.setText(selectItemModel.getTitle());
                if (selectItemModel.isBool()) {
                    select_check_item_cb.setChecked(true);
                    select_check_item_cb.setClickable(false);
                }
                select_check_common_item.addView(select_check_item_view, j);
            }
            check_item.addView(common_check_item_view, i);
        }


    }

    AddItemPopupWindows carSelectPopupWindows;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_select_ll:
                initAddDatas();
                carSelectPopupWindows = new AddItemPopupWindows(mActivity, carTypeList, this,"选择车辆类型");
                // 显示PopupWindow
                carSelectPopupWindows.showAtLocation(mActivity.findViewById(R.id.activity_select_check_item),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    ArrayList<String> carTypeList ;
    private void initAddDatas() {
        carTypeList = new ArrayList<>();
        for (int i = 0; i < selectCar.length; i++) {
            carTypeList.add(selectCar[i]);
        }
    }

    @Override
    public void getTitle(String title) {
       TextView car_tv = (TextView) car_select_ll.findViewById(R.id.car_tv);
        car_tv.setText(title);
        carSelectPopupWindows.dismiss();
    }
}
