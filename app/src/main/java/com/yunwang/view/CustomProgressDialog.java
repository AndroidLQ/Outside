package com.yunwang.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunwang.R;

/**
 * Created by Administrator on 2016/6/22.
 */
public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 创建dialog
     *
     * @param context                上下文
     * @param cancelable             点击是否消失
     * @param canceledOnTouchOutside 点击外围是否消失
     * @return
     */
    public static CustomProgressDialog createDialog(Context context, String dialogMessage, boolean cancelable, boolean canceledOnTouchOutside) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, null);
        TextView tvDialog = (TextView) dialogView.findViewById(R.id.tv_dialog);
        tvDialog.setText(dialogMessage);

        ImageView ivDialog = (ImageView) dialogView.findViewById(R.id.iv_dialog);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.dialog_rotate);
        ivDialog.startAnimation(animation);

        CustomProgressDialog dialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
        //设置布局
        dialog.setContentView(dialogView);
        //点击是否消失
        dialog.setCancelable(cancelable);
        //点击外围是否消失
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.7f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    /**
     * 设置文字的显示信息
     *
     * @param context
     * @param message
     */
    public void setDialogMessage(Context context, int message) {
        TextView tvDialog = (TextView) findViewById(R.id.tv_dialog);
        tvDialog.setText(context.getResources().getString(message));
    }
}
