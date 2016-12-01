package com.yunwang.manager;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunwang.R;

/**
 * Created by Administrator on 2016/6/20.
 * Toast管理类
 */
public class ToastManager {

    public static final int GRAVITY_CENTER = 1;

    public static final int GRAVITY_TOP = 2;

    public static final int GRAVITY_CENTER_HORIZONTAL = 3;

    public static final int GRAVITY_NO_GRAVITY = 4;

    public static final int GRAVITY_BOTTOM = 5;

    public static final int GRAVITY_CENTER_VERTICAL = 6;

    private static ToastManager toastManager;
    //土司
    private Toast toast;
    //上下文
    private Context context;

    private ToastManager(Context context) {
        this.context = context;
        toast = new Toast(context);
    }

    //单利模式
    public static ToastManager createToast(Context context) {
        if (toastManager == null) {
            toastManager = new ToastManager(context);
        }
        return toastManager;
    }

    /**
     * 显示toast
     *
     * @param message toast信息
     * @param gravity 屏幕位置
     */
    public void showToast(String message, int gravity) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_toast_message);
        ImageView ivToast = (ImageView) view.findViewById(R.id.iv_toast);
        tvMessage.setText(message);
        //TODO 可以不添加
//        ivToast.setImageResource(R.mipmap.ic_launcher);

        selectToastGravity(toast, gravity);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    /**
     * 显示toast
     *
     * @param message toast信息
     * @param gravity 屏幕位置
     */
    public void showToast(int message, int gravity) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_toast_message);
        ImageView ivToast = (ImageView) view.findViewById(R.id.iv_toast);
        tvMessage.setText(context.getResources().getString(message));

        selectToastGravity(toast, gravity);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    /**
     * 设置Toast在屏幕的位置
     *
     * @param toast
     * @param gravity
     */
    private void selectToastGravity(Toast toast, int gravity) {
        if (toast == null) {
            return;
        }
        int tempGravity = 0;
        switch (gravity) {
            case GRAVITY_CENTER:
                tempGravity = Gravity.CENTER;
                break;
            case GRAVITY_TOP:
                tempGravity = Gravity.TOP;
                break;
            case GRAVITY_CENTER_HORIZONTAL:
                tempGravity = Gravity.CENTER_HORIZONTAL;
                break;
            case GRAVITY_NO_GRAVITY:
                tempGravity = Gravity.NO_GRAVITY;
                break;
            case GRAVITY_BOTTOM:
                tempGravity = Gravity.BOTTOM;
                break;
            case GRAVITY_CENTER_VERTICAL:
                tempGravity = Gravity.CENTER_VERTICAL;
                break;
        }
        toast.setGravity(tempGravity, 0, 0);
    }

    /**
     * 取消toast
     */
    public void cancelToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
