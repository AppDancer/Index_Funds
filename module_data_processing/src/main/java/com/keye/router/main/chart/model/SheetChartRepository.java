package com.keye.router.main.chart.model;

import com.keye.router.main.chart.ISheetsContract;
import com.keye.router.main.excel.SheetRepository;
import com.keye.router.main.excel.bean.IndexFundsBean;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2019-08-11.
 */

public class SheetChartRepository implements ISheetsContract.IVideosRepository {
    private final SheetRepository sheetRepository;

    public SheetChartRepository() {
        sheetRepository=SheetRepository.getInstance();
    }

    @Override
    public Observable loadSheetDB(String name) {
        return sheetRepository.loadSheets(name);
    }
}
