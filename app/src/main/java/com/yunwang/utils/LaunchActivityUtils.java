package com.yunwang.utils;

import android.app.Activity;
import android.content.Intent;

import com.yunwang.R;

/**
 * Created by FSI on 2016/3/30.
 * 启动Activity的帮助类
 */
public class LaunchActivityUtils {

    /**
     *不带返回值的界面跳转(使用平移的动画效果)
     * @param from  从哪个Activity进行跳转
     * @param to    跳转到哪个Activity
     * @param intent  意图
     */
    public static void startActivity(Activity from,Class<?> to,Intent intent){
        if(intent == null){
            intent = new Intent();
        }
        intent.setClass(from,to);
        from.startActivity(intent);
        from.overridePendingTransition(R.anim.in_form_left, R.anim.out_of_right);
    }

    /**
     * 带返回值的界面跳转(使用平移的动画效果)
     * @param from  从哪个Activity进行跳转
     * @param to    跳转到哪个Activity
     * @param intent  意图
     * @param requestCode  请求码
     */
    public static  void startActivityForResult(Activity from,Class<?> to,Intent intent,int requestCode){
        if(intent == null){
            intent = new Intent();
        }
        intent.setClass(from,to);
        from.startActivityForResult(intent, requestCode);
        from.overridePendingTransition(R.anim.in_form_left, R.anim.out_of_right);
    }



}
