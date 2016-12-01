package com.yunwang.request;

import org.xutils.common.Callback;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
//上传的回调
public abstract class JWUploadCallback {

    public void onStarted() {
    }

    public void onWaiting() {
    }

    public void onCancelled(Callback.CancelledException cex) {
    }

    public void onLoading(long total, long current, float progress, boolean isUploading) {
    }

    public abstract void onSuccess(String result);

    public abstract void onSuccessArrays(List<String> results);

    public void onError(Throwable ex, boolean isOnCallback) {
    }

    public void onFinished() {
    }
}
