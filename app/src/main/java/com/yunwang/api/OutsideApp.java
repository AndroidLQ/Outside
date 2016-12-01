package com.yunwang.api;

/**
 * Created by Administrator on 2016/11/21.
 */

//所以的API接口
public class OutsideApp {

    private static String BASE_URL;//基类的url

    //正式上线的时候需要换回1表示正式环境(0为测试环境，1为正式环境)
    private static final int SELECT_URL = 0;

    static {
        switch (SELECT_URL) {
            case 0:
                break;
            case 1:
                break;
        }
    }

    //登录接口
}
