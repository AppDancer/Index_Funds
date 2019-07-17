package com.keye.router.main.excel.base;

import com.keye.router.lib_common.base.db.IDBHelper;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 2019-07-17.
 */

public interface IParseExcel<T>  {

    Observable<T> analyzeXls(String filePath);


}
