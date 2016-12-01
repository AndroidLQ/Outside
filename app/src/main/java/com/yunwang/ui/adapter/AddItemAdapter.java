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
 * Created by a on 2016/11/29.
 */

public class AddItemAdapter extends BaseRecyclerViewAdapter<String> {

    @Override
    protected int getLayoutResource() {
        return R.layout.outside_photo_popupwindow_recycview_item;
    }

    private Context mContext;
    public AddItemAdapter(Context context, ArrayList<String> datas) {
        super(context, datas);
        this.mContext = context;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new ViewHolder(inflater.inflate(getLayoutResource(), parent, false));

        return holder;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof AddItemAdapter.ViewHolder) {
            AddItemAdapter.ViewHolder viewHolder = (AddItemAdapter.ViewHolder) holder;
            viewHolder.title.setText(datas.get(position));

        }
    }

    private class ViewHolder extends BaseViewHolder {
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_tv);
        }
    }

}
