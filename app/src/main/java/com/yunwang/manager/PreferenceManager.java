package com.yunwang.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceManager {
    private static Editor editor;
    private static SharedPreferences sp;
    private static final String CONFIG = "config";//配置信息表
    private static final String USER_INFO = "user_info";//用户基本信息表

    private static SharedPreferences getPreferencesInstance(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sp;
    }

    private static Editor getEditorInstance(Context context) {

        if (editor == null) {
            editor = getPreferencesInstance(context).edit();
        }
        return editor;
    }


    public static void setString(Context context, String key, String value) {
        getEditorInstance(context).putString(key, value).commit();
    }

    public static void setBoolean(Context context, String key, boolean value) {
        getEditorInstance(context).putBoolean(key, value).commit();
    }

    public static void setInt(Context context, String key, int value) {
        getEditorInstance(context).putInt(key, value).commit();
    }

    public static void setLong(Context context, String key, long value) {
        getEditorInstance(context).putLong(key, value).commit();
    }


    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    ;

    public static String getString(Context context, String key, String defValue) {
        return getPreferencesInstance(context).getString(key, defValue);
    }

    ;

    public static boolean getBoolean(Context context, String key) {
        return getPreferencesInstance(context).getBoolean(key, false);
    }

    public static int getInt(Context context, String key) {
        return getPreferencesInstance(context).getInt(key, -1);
    }

    public static long getLong(Context context, String key) {
        return getPreferencesInstance(context).getLong(key, -1);
    }

    /**
     * 根据key值去清除相应的数据
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        getEditorInstance(context).remove(key);
    }
}
