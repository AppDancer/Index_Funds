package com.keye.router.main.excel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.keye.router.main.excel.bean.DaoMaster;
import com.keye.router.main.excel.bean.DaoSession;
import com.keye.router.main.excel.bean.Sheet;

import org.greenrobot.greendao.rx.RxDao;

/**
 * Created by Administrator on 2019-07-22.
 */

public class ExcelDBHandler {
    private DaoSession daoSession;

    private static ExcelDBHandler instance;

    public static ExcelDBHandler getInstance() {
        if (instance == null) {
            synchronized (ExcelDBHandler.class) {
                if (instance == null) {
                    instance = new ExcelDBHandler();
                }
            }
        }
        return instance;
    }

//    public void initData() {
//        DaoSession session = getDaoSession();
//        RxDao<Sheet, Long> sheetDao = session.getSheetDao().rx();
//
//    }

   public void initSheetDB(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "IndexFunds.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
