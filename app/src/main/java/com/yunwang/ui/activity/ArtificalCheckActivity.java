package com.yunwang.ui.activity;


import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.view.TitleBarView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_artifical_check)
public class ArtificalCheckActivity extends BaseActivty {

    private String item_text[] = new String[]{"车外廓长（cm）", "车外廓宽（cm）", "车外廓高（cm）", "整备质量（kg）", "机动车所有人", "手机号码", "联系地址", "邮政编码", "检验员建议"};
    private String et_hint[] = new String[]{"请输入车外廓长（cm）", "请输入车外廓宽（cm）", "请输入车外廓高（cm）", "请输入整备质量（kg）", "请输入机动车所有人", "请输入手机号码", "请输入联系地址", "请输入邮政编码", "请输入检验员建议"};

    @ViewInject(R.id.activity_artifical_check)
    LinearLayout ll_artfical_check;

    @ViewInject(R.id.artifical_item1)
    View chewai_length;

    @ViewInject(R.id.artifical_item2)
    View chewai_width;

    @ViewInject(R.id.artifical_item3)
    View chewai_height;

    @ViewInject(R.id.artifical_item4)
    View artifical_zhiliang;

    @ViewInject(R.id.artifical_item5)
    View artifical_people;

    @ViewInject(R.id.artifical_item6)
    View artifical_phone_number;

    @ViewInject(R.id.artifical_item7)
    View artifical_address;

    @ViewInject(R.id.artifical_item8)
    View artfical_youbian;

    @ViewInject(R.id.artifical_item9)
    View artfical_yijian;

    private EditText length_et;
    private EditText width_et;
    private EditText height_et;
    private EditText zhiliang_et;
    private EditText people_et;
    private EditText phone_et;
    private EditText address_et;
    private EditText youbian_et;
    private EditText yijian_et;

    @Override
    protected void initView() {
        ll_artfical_check.addView(titleBarView, 0);
        titleBarView.setTitleText("人工检验项目\n（贵A09W95）");
        titleBarView.setAllViewGone();
        titleBarView.setRightText("确定");
        titleBarView.setOnRightListener(new TitleBarView.MyOnClickListener() {
            @Override
            public void onRightClick() {
                Toast.makeText(mActivity,"点击了确定",Toast.LENGTH_LONG).show();
            }
        });

        for (int i = 0; i < item_text.length; i++) {
            switch (i) {
                case 0:
                    TextView item0_tv = (TextView) chewai_length.findViewById(R.id.common_tv);
                    item0_tv.setText(item_text[i]);

                    length_et = (EditText) chewai_length.findViewById(R.id.common_et);
                    length_et.setHint(et_hint[i]);
                    break;
                case 1:
                    TextView item1_tv = (TextView) chewai_width.findViewById(R.id.common_tv);
                    item1_tv.setText(item_text[i]);

                    width_et = (EditText) chewai_width.findViewById(R.id.common_et);
                    width_et.setHint(et_hint[i]);
                    break;
                case 2:
                    TextView item2_tv = (TextView) chewai_height.findViewById(R.id.common_tv);
                    item2_tv.setText(item_text[i]);

                    height_et = (EditText) chewai_height.findViewById(R.id.common_et);
                    height_et.setHint(et_hint[i]);
                    break;
                case 3:
                    TextView item3_tv = (TextView) artifical_zhiliang.findViewById(R.id.common_tv);
                    item3_tv.setText(item_text[i]);

                    zhiliang_et = (EditText) artifical_zhiliang.findViewById(R.id.common_et);
                    zhiliang_et.setHint(et_hint[i]);
                    break;
                case 4:
                    TextView item4_tv = (TextView) artifical_people.findViewById(R.id.common_tv);
                    item4_tv.setText(item_text[i]);

                    people_et = (EditText) artifical_people.findViewById(R.id.common_et);
                    people_et.setHint(et_hint[i]);
                    break;
                case 5:
                    TextView item5_tv = (TextView) artifical_phone_number.findViewById(R.id.common_tv);
                    item5_tv.setText(item_text[i]);

                    phone_et = (EditText) artifical_phone_number.findViewById(R.id.common_et);
                    phone_et.setHint(et_hint[i]);
                    break;
                case 6:
                    TextView item6_tv = (TextView) artifical_address.findViewById(R.id.common_tv);
                    item6_tv.setText(item_text[i]);

                    address_et = (EditText) artifical_address.findViewById(R.id.common_et);
                    address_et.setHint(et_hint[i]);
                    break;
                case 7:
                    TextView item7_tv = (TextView) artfical_youbian.findViewById(R.id.common_tv);
                    item7_tv.setText(item_text[i]);

                    youbian_et = (EditText) artfical_youbian.findViewById(R.id.common_et);
                    youbian_et.setHint(et_hint[i]);
                    break;
                case 8:
                    TextView item8_tv = (TextView) artfical_yijian.findViewById(R.id.common_tv);
                    item8_tv.setText(item_text[i]);

                    yijian_et = (EditText) artfical_yijian.findViewById(R.id.common_et);
                    yijian_et.setHint(et_hint[i]);
                    break;
            }

        }

    }

    @Override
    protected void loadData() {

    }

}
