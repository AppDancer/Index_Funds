package com.keye.router.main.excel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.keye.router.lib_common.base.utils.AppLogs;
import com.keye.router.main.excel.SheetRepository;
import com.keye.router.main.excel.bean.DaoMaster;
import com.keye.router.main.excel.bean.DaoSession;
import com.keye.router.main.excel.bean.IndexFundsBean;
import com.keye.router.main.excel.constants.Constants;
import com.keye.router.main.excel.db.base.IDBHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2019-07-22.
 */

public class ExcelDBHandler implements IDBHandler {
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
//        RxDao<IndexFundsBean, Long> sheetDao = session.getSheetDao().rx();
//
//    }

    private void initSheetDB(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "IndexFunds.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void checkInitSheet(Context context) {
        final long startTime = System.currentTimeMillis();
        SheetRepository sheetRepository = new SheetRepository();
        sheetRepository.analyzeXls(Constants.SHANGHAI_FUNDS_PATH)
                .subscribe(new Action1<Map<String, List<IndexFundsBean>>>() {
                    @Override
                    public void call(Map<String, List<IndexFundsBean>> data) {
                        Iterator<Map.Entry<String, List<IndexFundsBean>>> iterator = data.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, List<IndexFundsBean>> sheet = iterator.next();
                            List<IndexFundsBean> value = sheet.getValue();
                            daoSession.getIndexFundsBeanDao().rx().insertInTx(value);
//                            AppLogs.d(Constants.DB_LOG_KEY,value.toString());
                            AppLogs.d(Constants.DB_LOG_KEY,"解析总时长：" + (System.currentTimeMillis() - startTime));
                        }
                    }
                });
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void init(Context context) {
        initSheetDB(context);
    }

    @Override
    public void updata() {

    }

    @Override
    public void delete() {

    }
}
