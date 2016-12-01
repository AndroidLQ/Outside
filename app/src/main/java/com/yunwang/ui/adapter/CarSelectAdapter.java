package com.yunwang.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunwang.R;
import com.yunwang.base.BaseRecyclerViewAdapter;
import com.yunwang.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/23.
 */

//选择车的适配器
public class CarSelectAdapter extends BaseRecyclerViewAdapter<String> {

    public CarSelectAdapter(Context context, ArrayList<String> datas) {
        super(context, datas);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new ViewHolder(inflater.inflate(getLayoutResource(), parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvCarType.setText(datas.get(position));
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.item_car_type;
    }

    private class ViewHolder extends BaseViewHolder {

        private TextView tvCarType;//车的种类

        public ViewHolder(View itemView) {
            super(itemView);
            tvCarType = (TextView) itemView.findViewById(R.id.tv_car_type);
        }
    }
}
