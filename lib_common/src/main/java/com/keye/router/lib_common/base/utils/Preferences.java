package com.keye.router.lib_common.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;


/**
 * SharedPreferences工具类
 * Created by wcy on 2015/11/28.
 */
public class Preferences {
    private static Context sContext;
    private static SharedPreferences mPreferences;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static void saveBoolean(String key, boolean value) {
        getPreferences().edit().putBoolean(key, value).apply();
    }

    public static int getInt(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static void saveInt(String key, int value) {
        getPreferences().edit().putInt(key, value).apply();
    }

    public static long getLong(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static void saveLong(String key, long value) {
        getPreferences().edit().putLong(key, value).apply();
    }

    public static String getString(String key, @Nullable String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static void saveString(String key, @Nullable String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    public static float getFloat(String key, @Nullable float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    public static void saveFloat(String key, float value) {
        getPreferences().edit().putFloat(key, value).apply();
    }

    public static synchronized SharedPreferences getPreferences() {
        if (mPreferences == null) {
            mPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
        }
        return mPreferences;
    }
}
