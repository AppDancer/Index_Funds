package com.keye.router.main.excel.base;


import rx.Observable;

/**
 * Created by Administrator on 2019-07-17.
 */

public interface IParseExcel<T>  {

    Observable<T> analyzeXls(String filePath);


}
