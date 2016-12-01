package com.yunwang.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunwang.R;
import com.yunwang.base.BaseActivty;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_other_message)
public class OtherMessageActivity extends BaseActivty {

    private String item_text[] = new String[]{"里程表读书（km）", "手机号码", "联系地址"};
    private String et_hint[] = new String[]{"请输入里程表读书（km）", "请输入手机号码", "请输入联系地址"};

    @ViewInject(R.id.activity_other_message)
    LinearLayout ll_other_message;

    @ViewInject(R.id.other_message_mileage)
    View other_message_mileage;

    @ViewInject(R.id.other_message_phone)
    View other_message_phone;

    @ViewInject(R.id.other_message_address)
    View other_message_address;

    private EditText mileage_et;
    private EditText phone_et;
    private EditText address_et;


    @Override
    protected void initView() {
        ll_other_message.addView(titleBarView,0);
        titleBarView.setTitleText("其他信息录入\n（贵A09W95）");
        titleBarView.setAllViewGone();
        titleBarView.setRightText("确定");

        for (int i = 0; i < item_text.length; i++) {
            switch (i) {
                case 0:
                    TextView item0_tv = (TextView) other_message_mileage.findViewById(R.id.common_tv);
                    item0_tv.setText(item_text[i]);

                    mileage_et = (EditText) other_message_mileage.findViewById(R.id.common_et);
                    mileage_et.setHint(et_hint[i]);
                    break;
                case 1:
                    TextView item1_tv = (TextView) other_message_phone.findViewById(R.id.common_tv);
                    item1_tv.setText(item_text[i]);

                    phone_et = (EditText) other_message_phone.findViewById(R.id.common_et);
                    phone_et.setHint(et_hint[i]);
                    break;
                case 2:
                    TextView item2_tv = (TextView) other_message_address.findViewById(R.id.common_tv);
                    item2_tv.setText(item_text[i]);

                    address_et = (EditText) other_message_address.findViewById(R.id.common_et);
                    address_et.setHint(et_hint[i]);
                    break;
            }
        }
    }

    @Override
    protected void loadData() {

    }
}
