package com.keye.router.debug;

import android.database.sqlite.SQLiteDatabase;

import com.keye.router.lib_common.base.BaseApplication;
import com.keye.router.main.excel.bean.DaoMaster;
import com.keye.router.main.excel.bean.DaoSession;
import com.keye.router.main.excel.bean.Sheet;

import org.greenrobot.greendao.rx.RxDao;


/**
 * Created by Administrator on 2018-06-21.
 */

public class DebugMainApplication extends BaseApplication {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();

        initData();
    }

    private void initData() {
        DaoSession session = getDaoSession();
        RxDao<Sheet, Long> sheetDao = session.getSheetDao().rx();

    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "IndexFunds.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
