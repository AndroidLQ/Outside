package com.yunwang.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.interfaces.SelectItemInterface;
import com.yunwang.manager.GetPhotoManager;
import com.yunwang.model.OutsidePhotoModel;
import com.yunwang.ui.adapter.OutsidePhotoAdapter;
import com.yunwang.utils.Util;
import com.yunwang.view.AddItemPopupWindows;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_outside_photo)
public class OutsidePhotoActivity extends BaseActivty implements OnItemClickListener, View.OnClickListener,SelectItemInterface{
    private String TAG = this.getClass().getSimpleName().toString();
    private String title[] = new String[]{"车厢内部照片","车厢内部照片","车厢内部照片","车厢内部照片","车厢内部照片","车厢内部照片"};
    private ArrayList<OutsidePhotoModel> datas = null;
    private OutsidePhotoAdapter outsidePhotoAdapter;
    private GetPhotoManager getPhotoManager;
    private int selectPosition;

    private AddItemPopupWindows addItemPopupWindows;
    private ArrayList<String> addDatas;
    private String addTitle[] = new String[]{"车厢内部照片1","车厢内部照片2","车厢内部照片3","车厢内部照片4","车厢内部照片5","车厢内部照片6","车厢内部照片","车厢内部照片","车厢内部照片","车厢内部照片","车厢内部照片","车厢内部照片"};

    @ViewInject(R.id.outside_photo_pz_tv)
    private TextView pz_tv;

    @ViewInject(R.id.ll_back)
    LinearLayout ll_back;

    @ViewInject(R.id.outside_photo_time)
    TextView outside_photo_time_tv;

    @ViewInject(R.id.recycler_view)
    LRecyclerView recyclerView;

    @ViewInject(R.id.more_btn)
    Button more_btn;


    @Override
    protected void initView() {
        more_btn.setOnClickListener(this);
        ll_back.setOnClickListener(this);

    }

    @Override
    protected void loadData() {
        initDatas();

        getPhotoManager = new GetPhotoManager(this);
        initRecycleview();
    }

    public void initRecycleview(){
        //设置布局方式
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        outsidePhotoAdapter = new OutsidePhotoAdapter(this, datas);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(outsidePhotoAdapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        //允许下拉刷新
        recyclerView.setPullRefreshEnabled(false);
        //进行监听
        lRecyclerViewAdapter.setOnItemClickListener(this);
    }

    PopupWindow check_upload;
    int mWidth;

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

    @Override
    protected void onPause() {
        super.onPause();
        dismissPopupWindow();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    public void dismissPopupWindow() {
        if (check_upload != null && check_upload.isShowing()) {
            check_upload.dismiss();
            Util.backgroundAlpha(mActivity,1f);
        }
    }

    public void initDatas() {
        datas = new ArrayList<>();
        OutsidePhotoModel outsidePhotoModel = null;
        for (int i = 0; i < title.length; i++) {
            outsidePhotoModel = new OutsidePhotoModel();
            outsidePhotoModel.setImagePath("");
            outsidePhotoModel.setTitle(title[i]);
            outsidePhotoModel.setBool(true);
            datas.add(outsidePhotoModel);
        }

        //最后一个
        addEndItem();

    }

    @Override
    public void onItemClick(View view, int position) {
        if (position != datas.size() - 1) {
            //跳转系统拍照
            getPhotoManager.chooseFromCamera();
            selectPosition = position;
        }else if(position == datas.size() - 1 ){
            initAddDatas();
            addItemPopupWindows = new AddItemPopupWindows(mActivity, addDatas,this);
            // 显示PopupWindow
            addItemPopupWindows.showAtLocation(mActivity.findViewById(R.id.activity_outside_photo),
                    Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        }

    }

    private void initAddDatas() {
        addDatas = new ArrayList<>();
        for (int i = 0 ; i < addTitle.length ; i++){
            addDatas.add(addTitle[i]);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getPhotoManager.hanlderActivityResult(requestCode, data, new GetPhotoManager.CropListener() {
                @Override
                public void onCropAfter(String imagePath, String imageSmallPath) {
                    Log.i("OutsidePhotoActivity", imagePath + "--------" + imageSmallPath);

                    OutsidePhotoModel outsidePhotoModel = new OutsidePhotoModel();
                    outsidePhotoModel.setImagePath(imagePath);
                    outsidePhotoModel.setTitle(datas.get(selectPosition).getTitle());
                    outsidePhotoModel.setBool(datas.get(selectPosition).isBool());
                    datas.set(selectPosition, outsidePhotoModel);

                    initRecycleview();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_btn:
                Util.backgroundAlpha(mActivity,1f);
                if(check_upload != null && check_upload.isShowing()){
                    dismissPopupWindow();
                }else{
                    showPopupWindow(v);
                }
                break;
            //检查外观
            case R.id.check_outside_btn:
                startActivity(mActivity, OutsideCheckItemActivity.class, null, 0, false);

                break;
            //上传
            case R.id.upload_photo_btn:
                Toast.makeText(mActivity,"点击上传",Toast.LENGTH_LONG).show();
                break;
            case R.id.ll_back:
                finish();
                mActivity.overridePendingTransition(R.anim.in_form_left_back, R.anim.out_of_right_back);
                break;
        }

    }

    public void addEndItem(){
        //最后一个
        OutsidePhotoModel outsidePhotoModel = new OutsidePhotoModel();
        outsidePhotoModel.setImagePath("");
        outsidePhotoModel.setTitle("");
        datas.add(outsidePhotoModel);
    }

    @Override
    public void getTitle(String title) {
        //先移除否则会混乱
        recyclerView.removeAllViews();
        OutsidePhotoModel outsidePhotoModel = new OutsidePhotoModel();
        outsidePhotoModel.setImagePath("");
        outsidePhotoModel.setTitle(title);
        outsidePhotoModel.setBool(false);
        datas.remove(datas.size()-1);
        datas.add(outsidePhotoModel);
        addEndItem();

        initRecycleview();
        addItemPopupWindows.dismiss();
    }
}