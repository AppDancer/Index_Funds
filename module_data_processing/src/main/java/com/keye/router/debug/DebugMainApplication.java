package com.keye.router.debug;

import android.database.sqlite.SQLiteDatabase;

import com.keye.router.lib_common.base.BaseApplication;
import com.keye.router.main.excel.bean.DaoMaster;
import com.keye.router.main.excel.bean.DaoSession;
import com.keye.router.main.excel.bean.Sheet;
import com.keye.router.main.excel.db.ExcelDBHandler;

import org.greenrobot.greendao.rx.RxDao;


/**
 * Created by Administrator on 2018-06-21.
 */

public class DebugMainApplication extends BaseApplication {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        ExcelDBHandler.getInstance().initSheetDB(this);
    }

}
