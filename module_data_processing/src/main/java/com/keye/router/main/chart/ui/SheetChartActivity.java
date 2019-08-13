package com.keye.router.main.chart.ui;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.keye.router.main.R;
import com.keye.router.main.chart.ISheetsContract;
import com.keye.router.main.chart.model.SheetChartRepository;
import com.keye.router.main.chart.presenter.SheetChartPresenter;
import com.keye.router.main.databinding.ActivitySheetChartBinding;
import com.keye.router.main.excel.bean.IndexFundsBean;

public class SheetChartActivity extends AppCompatActivity implements SheetFragment.OnListFragmentInteractionListener {
    private static final int MODE_SHEET = 0;
    private static final int MODE_CHART = 1;

    private ISheetsContract.IVideosRepository repository;
    private ISheetsContract.ISheetPresenter presenter;

    private String sheetName;//图表指数名称
    public ActivitySheetChartBinding mDataBinding;
    private Fragment sheetFragment, chartFragment;
    private int fragmentMode = MODE_SHEET;//指数表格，显示模式
    private ISheetsContract.ISheetView currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_sheet_chart);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sheetName = extras.getString("sheet");
        }

        init();
        initView();
    }

    private void init() {
        sheetFragment = SheetFragment.newInstance(sheetName);
        repository = new SheetChartRepository();
        presenter = new SheetChartPresenter(this, repository);

        setFrament(fragmentMode);
    }

    private void setFrament(int mode) {
        if (currentView != null) {
            currentView.setPresenter(null);
        }
        switch (mode) {
            case MODE_SHEET:
                currentView = (ISheetsContract.ISheetView) sheetFragment;
                currentView.setPresenter(presenter);
                presenter.setView(currentView);
                break;
            case MODE_CHART:
                currentView = (ISheetsContract.ISheetView) chartFragment;
                currentView.setPresenter(presenter);
                presenter.setView(currentView);
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mode == MODE_SHEET ? sheetFragment : chartFragment)
//                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void initView() {

    }


    @Override
    public void onListFragmentInteraction(int position, IndexFundsBean item) {

    }
}
