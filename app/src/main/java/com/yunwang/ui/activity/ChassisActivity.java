package com.yunwang.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.utils.Util;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_chassis)
public class ChassisActivity extends BaseActivty implements View.OnClickListener {

    @ViewInject(R.id.outside_photo_pz_tv)
    private TextView pz_tv;

    @ViewInject(R.id.ll_back)
    LinearLayout ll_back;

    @ViewInject(R.id.more_btn)
    Button more_btn;

    @ViewInject(R.id.chasis_check_start_btn)
    Button chassis_check_start_btn;

    @ViewInject(R.id.chasis_check_end_btn)
    Button getChassis_check_end_btn;

    @ViewInject(R.id.outside_title_tv)
    TextView title_tv;


    @Override

    protected void initView() {
        title_tv.setText("底盘动态");
        more_btn.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        chassis_check_start_btn.setOnClickListener(this);
        getChassis_check_end_btn.setOnClickListener(this);

    }

    @Override
    protected void loadData() {

    }

    PopupWindow check_upload;
    private void showPopupWindow(View view) {

        View contentview = LayoutInflater.from(this).inflate(R.layout.outside_photo_popupwindow_item, null);
        Button check_item_btn = (Button) contentview.findViewById(R.id.check_outside_btn);
        Button upload_btn = (Button) contentview.findViewById(R.id.upload_photo_btn);
        check_item_btn.setOnClickListener(this);
        upload_btn.setOnClickListener(this);


        check_upload = new PopupWindow(contentview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置点击窗口外边窗口消失
        check_upload.setOutsideTouchable(false);
        // 设置此参数获得焦点，否则无法点击
        check_upload.setFocusable(false);
        //comment by danielinbiti,如果添加了这行，那么标注1和标注2那两行不用加，加上这行的效果是点popupwindow的外边也能关闭
        check_upload.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        check_upload.setTouchable(true);
        Util.backgroundAlpha(mActivity,0.5f);

        check_upload.showAsDropDown(view,-120,0);
    }

    public void dismissPopupWindow() {
        if (check_upload != null && check_upload.isShowing()) {
            check_upload.dismiss();
            Util.backgroundAlpha(mActivity,1f);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissPopupWindow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                mActivity.overridePendingTransition(R.anim.in_form_left_back, R.anim.out_of_right_back);
                break;

            case R.id.more_btn:
                if(check_upload != null && check_upload.isShowing()){
                    dismissPopupWindow();
                }else{
                    showPopupWindow(v);
                }

                break;
            //检查外观
            case R.id.check_outside_btn:
                Toast.makeText(mActivity,"检测项",Toast.LENGTH_LONG).show();

                break;
            //上传
            case R.id.upload_photo_btn:
                Toast.makeText(mActivity,"点击上传",Toast.LENGTH_LONG).show();
                break;
            //检查底盘动态开始
            case R.id.chasis_check_start_btn:
                Toast.makeText(mActivity,"检测底盘动态开始",Toast.LENGTH_LONG).show();
                break;
            //检查底盘动态结束
            case R.id.chasis_check_end_btn:
                Toast.makeText(mActivity,"检测底盘动态结束",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
