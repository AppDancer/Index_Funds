package com.keye.router.main.chart.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keye.router.main.R;
import com.keye.router.main.chart.ISheetsContract;
import com.keye.router.main.databinding.SheetItemLayoutBinding;
import com.keye.router.main.excel.bean.IndexFundsBean;

import java.util.List;

public class SheetFragment extends Fragment implements ISheetsContract.ISheetView {

    private static final String KEY_SHEET_NAME = "sheet";
    private ISheetsContract.ISheetPresenter presenter;

    private String mSheetName;
    private OnListFragmentInteractionListener mListener;
    public SheetItemLayoutBinding binding;
    private SheetsAdapter adapter;

    public SheetFragment() {
    }

    @SuppressWarnings("unused")
    public static SheetFragment newInstance(String sheetName) {
        SheetFragment fragment = new SheetFragment();
        Bundle args = new Bundle();
        args.putString(KEY_SHEET_NAME, sheetName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSheetName = getArguments().getString(KEY_SHEET_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.sheet_item_layout, container, false);
        adapter = new SheetsAdapter(mListener);

        binding.sheetRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.sheetRecyclerview.setAdapter(adapter);

        if (presenter != null) {
            presenter.loadSheetDB(mSheetName);
        }
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(ISheetsContract.ISheetPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void updataData(List<IndexFundsBean> indexFundsBeans) {
        if (adapter != null) {
            adapter.setData(indexFundsBeans);
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(int position, IndexFundsBean item);
    }
}
