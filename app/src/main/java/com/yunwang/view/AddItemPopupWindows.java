package com.yunwang.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yunwang.R;
import com.yunwang.interfaces.SelectItemInterface;
import com.yunwang.ui.adapter.AddItemAdapter;

import java.util.ArrayList;

/**
 * Created by a on 2016/11/29.
 */

public class AddItemPopupWindows extends PopupWindow implements  OnItemClickListener {

    private static final String TAG = "AddItemPopupWindows";

    private View mView;
    private ArrayList<String> datas;
    private LRecyclerView recyclerView;
    private SelectItemInterface selectItemInterface;
    private Context mContext;

    public AddItemPopupWindows(Activity context,ArrayList<String> datas,SelectItemInterface selectItemInterface,String title_str) {
        super(context);

        this.datas = datas;
        this.mContext = context;
        this.selectItemInterface = selectItemInterface;

        Log.i(TAG, "FinishProjectPopupWindow 方法已被调用");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.outside_photo_popupwindow_view, null);
        TextView title = (TextView) mView.findViewById(R.id.title_tv);
        title.setText(title_str);

        initView(mView);

        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation);
//        实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xff000000);
////        设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
    }

    private void initView(View view) {
        recyclerView = (LRecyclerView) view.findViewById(R.id.recycler_view);
        //设置布局方式
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        AddItemAdapter adapter = new AddItemAdapter(mContext,datas);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        //允许下拉刷新
        recyclerView.setPullRefreshEnabled(false);
        //进行监听
        lRecyclerViewAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(View view, int position) {
        selectItemInterface.getTitle(datas.get(position));
    }
}
