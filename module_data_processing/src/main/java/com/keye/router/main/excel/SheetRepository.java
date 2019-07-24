package com.keye.router.main.excel;

import android.text.TextUtils;

import com.keye.router.main.excel.base.ISheetRepository;
import com.keye.router.main.excel.bean.Sheet;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2019-07-18.
 */

public class SheetRepository implements ISheetRepository<Map<String, List<List<String>>>, Sheet> {

    @Override
    public void saveSheet() {

    }

    @Override
    public List<Sheet> loadSheets() {
        return null;
    }

    @Override
    public Observable<Map<String, List<List<String>>>> analyzeXls(String filePath) {
        return Observable.just(filePath).flatMap(new Func1<String, Observable<Map<String, List<List<String>>>>>() {
            @Override
            public Observable<Map<String, List<List<String>>>> call(String filePath) {
                ExcelManager instance = ExcelManager.getInstance();
                if (TextUtils.isEmpty(filePath)) {
                    filePath = "C:\\Users\\Administrator\\Desktop\\Index_Funds\\IndexFunds\\module_data_processing\\src\\main\\assets\\上证指数_估值.xls";
                }
                Map<String, List<List<String>>> data = instance.analyzeXls(filePath);
                return Observable.just(data);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
