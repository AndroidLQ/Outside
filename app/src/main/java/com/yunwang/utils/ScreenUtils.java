package com.yunwang.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by a on 2016/11/25.
 */

public class ScreenUtils {

    /**
     * @Author: kobe
     * @CreteDate: 2015-4-1 下午6:25:29
     * @Title:
     * @Description:获取屏幕宽度
     * @ModifiedBy:
     * @param context
     * @return
     */
    public static int getScreenPiexWidth(Context context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * @Author: kobe
     * @CreteDate: 2015-4-1 下午6:25:29
     * @Title:
     * @Description:获取屏幕高度
     * @ModifiedBy:
     * @param context
     * @return
     */
    public static int getScreenPiexHeight(Context context)
    {
        DisplayMetrics  dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
