package com.yunwang.model;

/**
 * Created by Administrator on 2016/8/8.
 *
 * 网络请求的model
 *
 */
public class RequestModel extends Entity {

    public int code;//返回码

    public String msg;//返回信息

    public String result;//实体类

    @Override
    public String toString() {
        return "RequestModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
