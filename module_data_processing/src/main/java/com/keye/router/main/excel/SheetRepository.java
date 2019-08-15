package com.keye.router.main.excel;

import android.app.Application;
import android.text.TextUtils;

import com.keye.router.lib_common.base.AppConfig;
import com.keye.router.lib_common.base.BaseApplication;
import com.keye.router.lib_common.base.utils.AppLogs;
import com.keye.router.main.excel.base.ISheetRepository;
import com.keye.router.main.excel.bean.IndexFundsBean;
import com.keye.router.main.excel.bean.IndexFundsBeanDao;
import com.keye.router.main.excel.constants.Constants;
import com.keye.router.main.excel.constants.ProcessConfig;
import com.keye.router.main.excel.db.ExcelDBHandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.keye.router.main.excel.constants.Constants.ASSETS_HEADER;

/**
 * Created by Administrator on 2019-07-18.
 */

public class SheetRepository implements ISheetRepository<Map<String, List<IndexFundsBean>>, IndexFundsBean> {

    private static SheetRepository instance;

    public static SheetRepository getInstance() {
        if (instance == null) {
            synchronized (SheetRepository.class) {
                if (instance == null) {
                    instance = new SheetRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void saveSheet() {

    }

    @Override
    public Observable<List<IndexFundsBean>> loadSheets(String name) {
        return ExcelDBHandler.getInstance().getDaoSession().getIndexFundsBeanDao()
                .queryBuilder().where(IndexFundsBeanDao.Properties.IndexFundsName.eq(name))
                .rx().list().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Map<String, List<IndexFundsBean>>> analyzeXls(String filePath) {
        return Observable.just(filePath)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String filePath) {
                        if (/*!TextUtils.isEmpty(filePath) &&*/ filePath.startsWith(ASSETS_HEADER)) {//assets文件，先copy到sd卡
                            filePath = filePath.replace(ASSETS_HEADER, "");
                            String path = AppConfig.getRootDir() + ProcessConfig.XLS_FILE_DIR + filePath;
                            try {
                                InputStream in = BaseApplication.getInstance().getAssets().open(filePath);
                                File toFile = new File(path);
                                if (toFile.exists()) {
                                    toFile.delete();
                                }
                                toFile.createNewFile();
                                FileOutputStream fileOut = new FileOutputStream(toFile);
                                byte[] buffer = new byte[1024];
                                int byteCount = 0;
                                while ((byteCount = in.read(buffer)) != -1) {
                                    fileOut.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                                }
                                fileOut.flush();
                                fileOut.close();
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return path;
                        }
                        return filePath;
                    }
                })
                .flatMap(new Func1<String, Observable<Map<String, List<List<String>>>>>() {
                    @Override
                    public Observable<Map<String, List<List<String>>>> call(String filePath) {
                        ExcelManager instance = ExcelManager.getInstance();

                        Map<String, List<List<String>>> data = instance.analyzeXls(filePath);
                        return Observable.just(data);
                    }
                }).flatMap(new Func1<Map<String, List<List<String>>>, Observable<Map<String, List<IndexFundsBean>>>>() {
                    @Override
                    public Observable<Map<String, List<IndexFundsBean>>> call(Map<String, List<List<String>>> source) {//二维数组，转换为IndexFundsBean数组
                        Map<String, List<IndexFundsBean>> sheetData = new HashMap<>();
                        Iterator<Map.Entry<String, List<List<String>>>> sheetIterator = source.entrySet().iterator();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        while (sheetIterator.hasNext()) {

                            List<IndexFundsBean> indexFundsBeans = new ArrayList<>();
                            Map.Entry<String, List<List<String>>> sheet = sheetIterator.next();

                            String sheetName = sheet.getKey();
                            List<List<String>> indexFundsIterator = sheet.getValue();

                            for (int i = 1; i < indexFundsIterator.size(); i++) {

                                List<String> beanString = indexFundsIterator.get(i);
                                IndexFundsBean bean = new IndexFundsBean();
                                bean.setIndexFundsName(sheetName);
                                try {
                                    Date date = sdf.parse(beanString.get(0));
                                    bean.setDate(date);//截止日期
//                            AppLogs.d(Constants.DB_DB_log_keyLOG_KEY, date.toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
//                        long startTime = System.currentTimeMillis();
                                bean.setClosingPrice(NumberUtils.priceStringToNumber(beanString.get(1)));//收盘价
                                bean.setPE(NumberUtils.priceStringToNumber(beanString.get(2)));//市盈率
                                bean.setPB(NumberUtils.priceStringToNumber(beanString.get(3)));//市净率
                                bean.setPCF(NumberUtils.priceStringToNumber(beanString.get(4)));//市现率
                                bean.setPS(NumberUtils.priceStringToNumber(beanString.get(5)));//市销率
                                bean.setEPS(NumberUtils.priceStringToNumber(beanString.get(6)));//每股收益
                                bean.setNetAssets(NumberUtils.priceStringToNumber(beanString.get(7)));//每股净资产
                                bean.setROE((NumberUtils.priceStringToNumber(beanString.get(8))));//净资产收益率
                                bean.setGMV(NumberUtils.priceStringToNumber(beanString.get(9)));//总市值
                                bean.setCMV(NumberUtils.priceStringToNumber(beanString.get(10)));//流通市值
                                indexFundsBeans.add(bean);
//                        AppLogs.d(Constants.DB_LOG_KEY,"解析bean时长：" + (System.currentTimeMillis() - startTime));
                            }

                            sheetData.put(sheetName, indexFundsBeans);
                        }
                        return Observable.just(sheetData);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }


}
