package com.yunwang.request;

import org.xutils.common.Callback;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
//上传的回调
public abstract class JWDownloadCallback {

    public void onStarted() {
    }

    public void onWaiting() {
    }

    public void onCancelled(Callback.CancelledException cex) {
    }

    public void onLoading(long total, long current, float progress, boolean isUploading) {
    }

    public abstract void onSuccess(File result);

    public abstract void onSuccessArrays(List<File> results);

    public void onError(Throwable ex, boolean isOnCallback) {
    }

    public void onFinished() {
    }
}
