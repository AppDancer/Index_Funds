package com.keye.router.lib_common.base;

import android.app.Application;

import com.keye.router.lib_common.base.utils.Preferences;

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
        Preferences.init(this);
        instance = this;
    }
}
