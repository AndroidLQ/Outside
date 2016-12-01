package com.yunwang.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.base.BaseRecyclerViewAdapter;
import com.yunwang.model.CheckItemModel;
import com.yunwang.ui.adapter.CarNumberAdapter;
import com.yunwang.ui.adapter.CheckItemAdapter;
import com.yunwang.view.TitleBarView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_outside_check_item)
public class OutsideCheckItemActivity extends BaseActivty implements View.OnClickListener,OnItemClickListener, LRecyclerView.LScrollListener{

    private ArrayList<CheckItemModel> datas = null;
    private String content[] = new String[]{"号牌号码/车辆类型","车辆品牌/型号","车辆识别码（或整车出厂编号）","发动机号码（或电动机号码）","车辆颜色和形状"};
    private int type[] = new int[]{1,1,2,2,1};
    private CheckItemAdapter checkItemAdapter;

    @ViewInject(R.id.recycler_view)
    LRecyclerView recyclerView;
    @ViewInject(R.id.input_item_btn)
    Button input_item_btn;
    @ViewInject(R.id.sure_btn)
    Button sure_btn;
    @ViewInject(R.id.outside_check_item_title)
    LinearLayout layout_ll;


    @Override
    protected void initView() {
        input_item_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        layout_ll.addView(titleBarView,0);
        titleBarView.setTitleText("外观检验项目\n(贵A09W95)");
        titleBarView.setAllViewGone();

    }

    @Override
    protected void loadData() {

        CheckItemModel checkItemModel = null;
        datas = new ArrayList<>();
        for (int i = 0 ; i < content.length ; i++){
            checkItemModel = new CheckItemModel();
            checkItemModel.setContent(content[i]);
            checkItemModel.setType(type[i]);
            datas.add(checkItemModel);
        }


        //设置布局方式
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        checkItemAdapter = new CheckItemAdapter(mActivity, datas);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(checkItemAdapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        //允许下拉刷新
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLScrollListener(this);
        //进行监听
        lRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //录入项目
            case R.id.input_item_btn:
                startActivity(mActivity,ArtificalCheckActivity.class,null,0,false);

                break;
            //确定
            case R.id.sure_btn:

                break;

        }
    }

    @Override
    public void onItemClick(View view, int position) {

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
}
