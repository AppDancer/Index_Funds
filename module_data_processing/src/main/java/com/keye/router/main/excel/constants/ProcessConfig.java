package com.keye.router.main.excel.constants;

import com.keye.router.lib_common.base.AppConfig;

/**
 * Created by Administrator on 2019-08-11.
 */

public class ProcessConfig extends AppConfig {
    //app 目录
    public static final String XLS_FILE_DIR = "xmls_source/";

    public void init() {
        checkAppDir(getRootDir() + XLS_FILE_DIR);
        checkAppDir(AppConfig.SDCard + AppConfig.ROOT_DIR);
    }
}
