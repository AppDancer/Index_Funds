package com.keye.router.main.chart.presenter;

import android.content.Context;

import com.keye.router.main.chart.ISheetsContract;

/**
 * Created by Administrator on 2019-08-11.
 */

public class SheetChartPresenter implements ISheetsContract.ISheetPresenter {
    private ISheetsContract.IVideosRepository repository;
    private ISheetsContract.ISheetView sheetView;
    private Context context;

    public SheetChartPresenter(Context context, ISheetsContract.IVideosRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    @Override
    public void setView(ISheetsContract.ISheetView sheetView) {
        this.sheetView = sheetView;
    }

    @Override
    public void start() {

    }
}
