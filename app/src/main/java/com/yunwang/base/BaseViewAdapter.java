package com.yunwang.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yunwang.config.ImageConfiguration;

import java.util.List;

/**
 * Created by Administrator on 2016/5/21 0021.
 */
public abstract class BaseViewAdapter<T> extends BaseAdapter {

    protected List<T> datas;
    protected LayoutInflater inflater;
    protected Context context;
    protected DisplayImageOptions options;
    protected ImageLoader imageLoader;

    public BaseViewAdapter(Context context, List<T> datas) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
        options = ImageConfiguration.getDisplayImageOptions();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return inflater.inflate(getLayoutResource(), viewGroup, false);
    }

    public abstract BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    protected abstract int getLayoutResource();
}
