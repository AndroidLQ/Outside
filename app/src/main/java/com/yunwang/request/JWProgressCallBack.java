package com.yunwang.request;


import org.xutils.common.Callback;

/**
 * Created by Administrator on 2016/6/20.
 * 进度条封装类
 */
public class JWProgressCallBack<ResultType> implements Callback.ProgressCallback<ResultType>{

    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }

    @Override
    public void onSuccess(ResultType result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
