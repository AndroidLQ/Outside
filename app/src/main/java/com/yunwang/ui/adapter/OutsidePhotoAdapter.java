package com.yunwang.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunwang.R;
import com.yunwang.base.BaseRecyclerViewAdapter;
import com.yunwang.base.BaseViewHolder;
import com.yunwang.model.OutsidePhotoModel;

import java.util.ArrayList;

/**
 * Created by a on 2016/11/25.
 */

public class OutsidePhotoAdapter extends BaseRecyclerViewAdapter<OutsidePhotoModel> {
    private Context mContext;
    public OutsidePhotoAdapter(Context context, ArrayList<OutsidePhotoModel> datas) {
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
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            OutsidePhotoModel outsidePhotoModel = datas.get(position);

            if(!outsidePhotoModel.getImagePath().equals("")){
                viewHolder.iv.setImageBitmap(BitmapFactory.decodeFile(outsidePhotoModel.getImagePath()));
            }

            if(position == datas.size() - 1 ){
                viewHolder.cb.setVisibility(View.GONE);
            }

            if (position == datas.size() - 1){
                viewHolder.iv.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.outside_photo_add));
            }

            viewHolder.title.setText(outsidePhotoModel.getTitle());

        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.outside_photo_recycview_item_view;
    }

    private class ViewHolder extends BaseViewHolder {

        private ImageView iv;
        private CheckBox cb;
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.outside_photo_iv);
            cb = (CheckBox) itemView.findViewById(R.id.outside_photo_cb);
            title = (TextView) itemView.findViewById(R.id.outside_photo_title);
        }
    }
}
