package com.keye.router.main.chart;

import com.keye.router.lib_common.base.base.BasePresenter;
import com.keye.router.lib_common.base.base.BaseView;

public interface ISheetsContract {

    interface ISheetView extends BaseView<ISheetPresenter> {

    }

    interface ISheetPresenter extends BasePresenter {
        void setView(ISheetView sheetView);
    }

    interface IVideosRepository {

    }
}
