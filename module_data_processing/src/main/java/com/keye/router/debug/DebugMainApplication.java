package com.keye.router.debug;

import com.keye.router.lib_common.base.AppConfig;
import com.keye.router.lib_common.base.BaseApplication;
import com.keye.router.lib_common.base.utils.PermissionUtils;
import com.keye.router.lib_common.base.utils.Preferences;
import com.keye.router.main.excel.bean.DaoSession;
import com.keye.router.main.excel.constants.ProcessConfig;
import com.keye.router.main.excel.db.ExcelDBHandler;

import static com.keye.router.main.excel.constants.Constants.IS_FIRST_INIT;


/**
 * Created by Administrator on 2018-06-21.
 */

public class DebugMainApplication extends BaseApplication {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        ExcelDBHandler.getInstance().init(this);
    }

}
