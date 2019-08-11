package com.keye.router.lib_common.base.utils;

import android.util.Log;

import com.keye.router.lib_common.base.AppConfig;

/**
 * Created by Administrator on 2019-08-11.
 */

public class AppLogs {

    public static void d(String key, String msg) {
        if (AppConfig.DEBUG) {
            Log.d(key, msg);
        }
    }

    public static void i(String key, String msg) {
        if (AppConfig.DEBUG) {
            Log.i(key, msg);
        }
    }

    public static void e(String key, String msg) {
        if (AppConfig.DEBUG) {
            Log.e(key, msg);
        }
    }
}
