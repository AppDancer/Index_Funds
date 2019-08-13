package com.keye.router.main.chart.presenter;

import android.content.Context;
import android.widget.Toast;

import com.keye.router.main.chart.ISheetsContract;
import com.keye.router.main.excel.bean.IndexFundsBean;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Administrator on 2019-08-11.
 */

public class SheetChartPresenter implements ISheetsContract.ISheetPresenter {
    private ISheetsContract.IVideosRepository repository;
    private ISheetsContract.ISheetView mView;
    private Context context;

    public SheetChartPresenter(Context context, ISheetsContract.IVideosRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    @Override
    public void setView(ISheetsContract.ISheetView sheetView) {
        this.mView = sheetView;
    }

    @Override
    public void loadSheetDB(String name) {
        repository.loadSheetDB(name).subscribe(new Action1<List<IndexFundsBean>>() {
            @Override
            public void call(List<IndexFundsBean> indexFundsBeans) {
                Toast.makeText(context,"查询数据："+indexFundsBeans.size(),Toast.LENGTH_LONG).show();
                if(mView!=null){
                    mView.updataData(indexFundsBeans);
                }
            }
        });
    }

    @Override
    public void start() {
    }
}
