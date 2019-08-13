package com.keye.router.main.chart;

import com.keye.router.lib_common.base.base.BasePresenter;
import com.keye.router.lib_common.base.base.BaseView;
import com.keye.router.main.excel.bean.IndexFundsBean;

import java.util.List;

import rx.Observable;

public interface ISheetsContract {

    interface ISheetView extends BaseView<ISheetPresenter> {
        void updataData(List<IndexFundsBean> indexFundsBeans);
    }

    interface ISheetPresenter extends BasePresenter {
        void setView(ISheetView sheetView);

        void loadSheetDB(String name);
    }

    interface IVideosRepository {
       Observable loadSheetDB(String name);
    }
}
