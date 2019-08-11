package com.keye.router.lib_common.base;

import android.os.Environment;

import com.keye.router.lib_common.BuildConfig;

import java.io.File;

/**
 * Created by Administrator on 2019-08-11.
 */

public class AppConfig {
    public static boolean DEBUG = BuildConfig.DEBUG && true;
    public static final String SDCard = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";

    //app 根目录
    public static final String ROOT_DIR = "IndexFunds/";

    public void checkAppDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getRootDir() {
        return SDCard + ROOT_DIR;
    }


}
