package com.yunwang.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunwang.R;
import com.yunwang.base.BaseRecyclerViewAdapter;
import com.yunwang.base.BaseViewHolder;
import com.yunwang.model.CarNumber;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/21.
 * 查询车辆的适配器
 */
public class CarNumberAdapter extends BaseRecyclerViewAdapter<CarNumber> {

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
            viewHolder.tvCarType.setText(datas.get(position).carType);
            viewHolder.tvCarNumber.setText(datas.get(position).carNumber);
            viewHolder.tvCarCheckStatus.setText(datas.get(position).carCheckStatus);
        }
    }

    public CarNumberAdapter(Context context, ArrayList<CarNumber> datas) {
        super(context, datas);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.item_car_number;
    }

    private class ViewHolder extends BaseViewHolder {

        private TextView tvCarType;//车的类型
        private TextView tvCarNumber;//车牌号
        private TextView tvCarCheckStatus;//车的检查状态

        public ViewHolder(View itemView) {
            super(itemView);
            tvCarType = (TextView) itemView.findViewById(R.id.tv_car_type);
            tvCarNumber = (TextView) itemView.findViewById(R.id.tv_car_number);
            tvCarCheckStatus = (TextView) itemView.findViewById(R.id.tv_car_check_status);
        }
    }
}
