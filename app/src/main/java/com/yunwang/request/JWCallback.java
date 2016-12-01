package com.yunwang.request;

import org.xutils.common.Callback;

import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public abstract class JWCallback<ResultType> {

    public void onStarted() {
    }

    public void onWaiting() {
    }

    public void onCancelled(Callback.CancelledException cex) {
    }

    public void onLoading(long total, long current, float progress, boolean isUploading) {
    }

    public abstract void onSuccess(ResultType result);

    public abstract void onSuccessArrays(List<ResultType> results);

    public void onError(Throwable ex, boolean isOnCallback){}

    public void onFinished() {
    }

}
