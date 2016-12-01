package com.yunwang.request;

import android.content.Context;

import com.yunwang.manager.ToastManager;

import org.xutils.ex.HttpException;

/**
 * Created by Administrator on 2016/6/20.
 */
public class HttpUtilsRequestError extends Throwable {

    private Context mContext;

    public HttpUtilsRequestError(Context context) {
        this.mContext = context;
    }

    public void onError(Throwable ex, boolean isOnCallback) {
        if (ex instanceof HttpException) {
            HttpException exception = (HttpException) ex;
            int responseCode = exception.getCode();
            String responseMsg = exception.getMessage();
            String errorResult = exception.getResult();

            ToastManager.createToast(mContext).showToast(
                    responseMsg, ToastManager.GRAVITY_CENTER);
        }
    }
}
