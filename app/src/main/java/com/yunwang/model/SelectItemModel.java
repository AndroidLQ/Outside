package com.yunwang.model;

/**
 * Created by Administrator on 2016/12/3.
 */

public class SelectItemModel {
    private String title;
    private boolean bool;

    public SelectItemModel() {

    }

    public SelectItemModel(String title, boolean bool) {
        this.title = title;
        this.bool = bool;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
