package com.keye.router.main.excel.base;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2019-07-18.
 */

public interface ISheetRepository<T,K> extends IParseExcel<T> {
    void saveSheet();

      Observable<List<K>> loadSheets(String name);

}
