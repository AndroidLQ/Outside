package com.yunwang.request;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yunwang.R;
import com.yunwang.manager.NetManager;
import com.yunwang.manager.ToastManager;
import com.yunwang.model.RequestModel;
import com.yunwang.utils.StringUtils;
import com.yunwang.view.CustomProgressDialog;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by Administrator on 2016/6/20.
 * 网络请求封装类
 */
public class HttpUtilsRequest {

    private static final int GET_CONNECT_TIME_OUT = 5 * 1000;//5秒连接超时

    private static final int POST_CONNECT_TIME_OUT = 10 * 1000;//5秒连接超时

    private static final int MAX_RETRY_COUNT = 3;//最大请求次数----3次

    private final static int MAX_DOWNLOAD_THREAD = 5;

    private final Executor executor = new PriorityExecutor(MAX_DOWNLOAD_THREAD, true);
    //请求参数
    private RequestParams params;

    private Callback.Cancelable cancelable;

    //私有构造器
    private HttpUtilsRequest() {
    }

    private static HttpUtilsRequest httpUtils;

    /**
     * 单利模式
     */
    public static HttpUtilsRequest getInstance() {
        if (httpUtils == null) {
            synchronized (HttpUtilsRequest.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtilsRequest();
                }
            }
        }
        return httpUtils;
    }

    private void setRequestParams(LRecyclerView mRecyclerView, Context context, String url, Map<String, String> maps) {
        int networkType = NetManager.getNetworkType(context);
        if (networkType == 0) {//表示没有连接网络
            ToastManager.createToast(context).showToast(R.string.net_is_not_connected,
                    ToastManager.GRAVITY_CENTER);
            if (null != mRecyclerView) {
                //加载完成
                mRecyclerView.refreshComplete();
            }
            return;
        }

        params = new RequestParams(url);
        params.setConnectTimeout(GET_CONNECT_TIME_OUT);
        params.setMaxRetryCount(MAX_RETRY_COUNT);
        if (maps != null) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 添加头部信息
     *
     * @param key   key
     * @param value value
     */
    private void addHeader(String key, String value) {
        params.addHeader(key, value);
    }

    private void addParams(String key, String value) {
        params.addBodyParameter(key, value);
    }

    /**
     * * 发送get请求
     *
     * @param url          网络请求地址
     * @param classOfT     实体类
     * @param dialog       进度条
     * @param maps         参数
     * @param callback     回掉
     * @param <ResultType> 实体类型
     * @return
     */
    public <ResultType> Callback.Cancelable get(final LRecyclerView mRecyclerView, Context context, String url, final Type classOfT,
                                                final CustomProgressDialog dialog, Map<String, String> maps, final JWCallback<ResultType> callback) {
        //设置参数
        setRequestParams(mRecyclerView, context, url, maps);
        if (dialog != null) {
            dialog.show();
        }
        if (params == null) {
            return null;
        }
        cancelable = x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                ResultType resultType = JSON.parseObject(result, classOfT);
                if (callback != null) {
                    callback.onSuccess(resultType);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (null != mRecyclerView) {
                    //加载完成
                    mRecyclerView.refreshComplete();
                }
               /* if (callback != null) {
                    callback.onError(ex, isOnCallback);
                }*/
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onCancelled(cex);
                }
                if (null != mRecyclerView) {
                    //加载完成
                    mRecyclerView.refreshComplete();
                }
            }

            @Override
            public void onFinished() {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onFinished();
                }
            }
        });
        return cancelable;
    }

    /**
     * 发送post请求
     *
     * @param url          网络请求地址
     * @param classOfT     实体类
     * @param dialog       进度条
     * @param maps         参数
     * @param callback     回掉
     * @param <ResultType> 实体类型
     * @return
     */
    public <ResultType> Callback.Cancelable post(final LRecyclerView mRecyclerView, final Context context, String url, final Type classOfT, final Class<ResultType> clazz,
                                                 final CustomProgressDialog dialog, Map<String, String> maps, final JWCallback<ResultType> callback) {

        //设置参数
        setRequestParams(mRecyclerView, context, url, maps);
        if (dialog != null) {
            dialog.show();
        }
        if (params == null) {
            return null;
        }
        cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (dialog != null) {
                    dialog.dismiss();
                }

//                if (!StringUtils.isChineseChar(result)) {
                //unicode转换成中文
                result = StringUtils.decodeUnicode(result);
//                }
            /*    JSONObject jsonObject = JSON.parseObject(result);
                boolean hasCode = jsonObject.containsKey("code");
                if (hasCode) {
                    String code = jsonObject.getString("code");
                    if (!TextUtils.isEmpty(code)) {
                        if ("200".equals(code)) {
                            boolean hasResult = jsonObject.containsKey("code");
                            if (hasResult) {
                                result = jsonObject.getString("result");
                                ResultType resultType = JSON.parseObject(result, classOfT);
                                if (callback != null) {
                                    callback.onSuccess(resultType);
                                }
                            }
                        } else {
                            boolean hasMsg = jsonObject.containsKey("msg");
                            if (hasMsg) {
                                String msg = jsonObject.getString("msg");
                                ToastManager.createToast(context).showToast(msg, ToastManager.GRAVITY_CENTER);
                            }
                        }
                    }
                }*/

                RequestModel requestModel = JSON.parseObject(result, RequestModel.class);
                if (requestModel != null) {
                    if (requestModel.code == 200) {
                        if (!TextUtils.isEmpty(requestModel.result)) {
                            JSONObject jsonObject = JSON.parseObject(result);
                            String tempResult = jsonObject.getString("result");
                            if (tempResult.startsWith("{")) {//判断为对象
                                ResultType resultType = JSON.parseObject(requestModel.result, classOfT);
                                if (callback != null) {
                                    callback.onSuccess(resultType);
                                }
                            } else if (tempResult.startsWith("[")) {//判断为数组
                                List<ResultType> resultTypes = JSON.parseArray(requestModel.result, clazz);
                                if (callback != null) {
                                    callback.onSuccessArrays(resultTypes);
                                }
                            } else if (tempResult.contains("false")) {
                                ResultType resultType = JSON.parseObject(result, classOfT);
                                if (callback != null) {
                                    callback.onSuccess(resultType);
                                }
                            }
                        } else {
                            //创建一个临时的JSON字符串,以便返回
                            String tempResult = "{\"code\":200,\"result\":null}";
                            ResultType resultType = JSON.parseObject(tempResult, classOfT);
                            if (callback != null) {
                                callback.onSuccess(resultType);
                            }
                        }
                    } else {
                        if (requestModel != null && requestModel.code == 300) {//300表示没有数据
                            if (!TextUtils.isEmpty(requestModel.msg)) {//表示密码和账号错误
                                ToastManager.createToast(context).showToast(requestModel.msg, Gravity.CENTER);
                                return;
                            } else {//表示没有数据
                                ResultType resultType = JSON.parseObject(result, classOfT);
                                if (callback != null) {
                                    callback.onSuccess(resultType);
                                }
                            }
                        }
//                        if (!TextUtils.isEmpty(requestModel.msg)) {
//                            ToastManager.createToast(context).showToast(requestModel.msg, ToastManager.GRAVITY_CENTER);
//                        }
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (null != mRecyclerView) {
                    //加载完成
                    mRecyclerView.refreshComplete();
                }
               /* if (callback != null) {
                    callback.onError(ex, isOnCallback);
                }*/
                ToastManager.createToast(context).showToast(R.string.net_connection_error, ToastManager.GRAVITY_CENTER);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onCancelled(cex);
                }
                if (null != mRecyclerView) {
                    //加载完成
                    mRecyclerView.refreshComplete();
                }
            }

            @Override
            public void onFinished() {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onFinished();
                }
            }
        });
        return cancelable;
    }

    /**
     * 上传文件
     *
     * @param url          网络请求地址
     * @param dialog       进度条
     * @param callback     回掉
     * @param <ResultType> 实体类型
     */
    public <ResultType> void uploadFile(final LRecyclerView mRecyclerView, final Context context,
                                        String url, final CustomProgressDialog dialog,
                                       /* final Type classOfT, String filePath, String key2,*/ File file,
                                       /* File file2, String fileType, String fileName,*/
                                        final JWUploadCallback callback) {
        //设置参数
        setRequestParams(mRecyclerView, context, url, null);
        if (params == null) {
            return;
        }
        // 加到url里的参数, http://xxxx/s?wd=xUtils
//            params.addQueryStringParameter("wd", "xUtils");
        // 添加到请求body体的参数, 只有POST, PUT, PATCH, DELETE请求支持.
        // params.addBodyParameter("wd", "xUtils");
        params.setConnectTimeout(POST_CONNECT_TIME_OUT);
        // 使用multipart表单上传文件
        params.setMultipart(true);

        //德托Dtaatrx配套使用
        params.addBodyParameter("id", file.getName());
        params.addBodyParameter("myfile", file);
        params.addHeader("Expect", "100-Continue");


        //name - 参数名
        //value - 可以是String, File, InputStream 或 byte[]
        //contentType - 可为null
        params.addBodyParameter(
                file.getName(),
                file,
                null); // 如果文件没有扩展名, 最好设置contentType参数.
    /*    try {
            params.addBodyParameter(
                    key2,
                    new FileInputStream(file2),
                    fileType,
                    fileName); // InputStream参数获取不到文件名, 最好设置, 除非服务端不关心这个参数.
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }*/
        if (dialog != null) {
            dialog.show();
        }
        cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String fileId) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onSuccess(fileId);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (null != mRecyclerView) {
                    //加载完成
                    mRecyclerView.refreshComplete();
                }
               /* if (callback != null) {
                    callback.onError(ex, isOnCallback);
                }*/
                ToastManager.createToast(context).showToast(R.string.net_connection_error, ToastManager.GRAVITY_CENTER);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onCancelled(cex);
                }
                if (null != mRecyclerView) {
                    //加载完成
                    mRecyclerView.refreshComplete();
                }
            }

            @Override
            public void onFinished() {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onFinished();
                }
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url          网络请求地址
     * @param dialog       进度条
     * @param filePath     文件路径
     * @param callback     回掉
     * @param <ResultType> 实体类型
     * @return
     */
    public <ResultType> Callback.Cancelable downLoad(final LRecyclerView mRecyclerView,
                                                     Context context, String url,
                                                     final CustomProgressDialog dialog, String filePath,
                                                     final JWDownloadCallback callback) {
        //设置参数
        setRequestParams(mRecyclerView, context, url, null);
        if (params == null) {
            return null;
        }
        params.setConnectTimeout(POST_CONNECT_TIME_OUT);
        params.setAutoResume(true);//自动恢复上次的下载
        //使用下载文件的名字
        //false使用自己定义的文件名，true表示使用xutils3下载文件的名字
        params.setAutoRename(true);
        params.setSaveFilePath(filePath);
        params.setExecutor(executor);
        params.setCancelFast(true);
        if (dialog != null) {
            dialog.show();
        }
//        params.setUseCookie(true);
        cancelable = x.http().get(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
//                ResultType resultType = JSON.parseObject(result.getAbsolutePath(), classOfT);
                LogUtil.e("**********onSuccess---------");
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onSuccess(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("**********onError---------");
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (null != mRecyclerView) {
                    //加载完成
                    mRecyclerView.refreshComplete();
                }
               /* if (callback != null) {
                    callback.onError(ex, isOnCallback);
                }*/
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("**********onCancelled---------");
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onCancelled(cex);
                }
                if (null != mRecyclerView) {
                    //加载完成
                    mRecyclerView.refreshComplete();
                }
            }

            @Override
            public void onFinished() {
                LogUtil.e("**********onFinished---------");
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onFinished();
                }
            }
        });
        return cancelable;
    }

    /**
     * 取消请求或者上传或者下载文件
     */
    public void requestCancel() {
        if (cancelable != null) {
            cancelable.cancel();
            cancelable = null;
        }
    }
}
