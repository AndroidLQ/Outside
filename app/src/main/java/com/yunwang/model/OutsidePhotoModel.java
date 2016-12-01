package com.yunwang.model;

/**
 * Created by Administrator on 2016/11/28.
 */

public class OutsidePhotoModel {
    //标题
    private String title;
    //图片途径
    private String imagePath;
    //选中标记
    private boolean bool;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
