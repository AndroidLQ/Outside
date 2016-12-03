package com.yunwang.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/3.
 */

public class SelectCommonModel {
    private int type;
    private ArrayList<SelectItemModel> data;

    public SelectCommonModel() {
    }

    public SelectCommonModel(int type, ArrayList<SelectItemModel> data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<SelectItemModel> getData() {
        return data;
    }

    public void setData(ArrayList<SelectItemModel> data) {
        this.data = data;
    }
}
