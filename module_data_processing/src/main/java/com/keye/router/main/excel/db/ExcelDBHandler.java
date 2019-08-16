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

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
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

    public void checkInitSheet(Context context, final CallBack callBack) {
        final long startTime = System.currentTimeMillis();
        SheetRepository sheetRepository = SheetRepository.getInstance();
        sheetRepository.analyzeXls(Constants.SHANGHAI_FUNDS_PATH)
                .flatMap(new Func1<Map<String, List<IndexFundsBean>>, Observable<Map.Entry<String, List<IndexFundsBean>>>>() {
                    @Override
                    public Observable<Map.Entry<String, List<IndexFundsBean>>> call(Map<String, List<IndexFundsBean>> data) {
                        return Observable.from(data.entrySet());//依次插入指数表
                    }
                })
                .flatMap(new Func1<Map.Entry<String, List<IndexFundsBean>>, Observable<Iterable<IndexFundsBean>>>() {
                    @Override
                    public Observable<Iterable<IndexFundsBean>> call(Map.Entry<String, List<IndexFundsBean>> sheet) {
                        List<IndexFundsBean> value = sheet.getValue();
                        return daoSession.getIndexFundsBeanDao().rx().insertInTx(value);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Iterable<IndexFundsBean>>() {
                    @Override
                    public void onCompleted() {
                        if (callBack != null)
                            callBack.onAnalyzeResult((System.currentTimeMillis() - startTime), true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callBack != null)
                            callBack.onAnalyzeResult((System.currentTimeMillis() - startTime), false);
                    }

                    @Override
                    public void onNext(Iterable<IndexFundsBean> indexFundsBeans) {
//                            AppLogs.d(Constants.DB_LOG_KEY,value.toString());
                        AppLogs.d(Constants.DB_LOG_KEY, "解析总时长：" + (System.currentTimeMillis() - startTime));
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

    public interface CallBack {
        void onAnalyzeResult(long time, boolean success);
    }
}
