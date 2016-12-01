package com.yunwang.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunwang.R;
import com.yunwang.base.BaseRecyclerViewAdapter;
import com.yunwang.base.BaseViewAdapter;
import com.yunwang.base.BaseViewHolder;
import com.yunwang.model.CheckItemModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/28.
 */

public class CheckItemAdapter extends BaseRecyclerViewAdapter<CheckItemModel> {

    private Context mContext;
    public CheckItemAdapter(Context context, ArrayList<CheckItemModel> datas) {
        super(context, datas);
        this.mContext = context;

    }



    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof CheckItemAdapter.ViewHolder) {
            CheckItemAdapter.ViewHolder viewHolder = (CheckItemAdapter.ViewHolder) holder;
            CheckItemModel checkItemModel = datas.get(position);
            switch (checkItemModel.getType()){
                case 1:
                    Drawable drawable_standard= mContext.getResources().getDrawable(R.drawable.check_item_standard);
                    /// 这一步必须要做,否则不会显示.
                    drawable_standard.setBounds(0, 0, drawable_standard.getMinimumWidth(), drawable_standard.getMinimumHeight());
                    viewHolder.type_tv.setCompoundDrawables(drawable_standard,null,null,null);
                    viewHolder.type_tv.setText("合格");
                    break;
                case 2:
                    Drawable drawable_buhege = mContext.getResources().getDrawable(R.drawable.check_item_buhege);
                    /// 这一步必须要做,否则不会显示.
                    drawable_buhege.setBounds(0, 0, drawable_buhege.getMinimumWidth(), drawable_buhege.getMinimumHeight());
                    viewHolder.type_tv.setCompoundDrawables(drawable_buhege,null,null,null);
                    viewHolder.type_tv.setText("不合格");
                    break;
            }

            viewHolder.content_tv.setText(checkItemModel.getContent());

        }
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new ViewHolder(inflater.inflate(getLayoutResource(), parent, false));
        return holder;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.check_item_recycview_item;
    }

    private class ViewHolder extends BaseViewHolder {

        private TextView content_tv;
        private TextView type_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            content_tv = (TextView) itemView.findViewById(R.id.check_item_content);
            type_tv = (TextView) itemView.findViewById(R.id.check_item_type);
        }
    }
}
