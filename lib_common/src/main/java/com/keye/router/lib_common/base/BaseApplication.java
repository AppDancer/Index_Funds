package com.keye.router.lib_common.base;

import android.app.Application;

/**
 * Created by Administrator on 2018-06-21.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
