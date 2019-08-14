package com.keye.router.main.chart.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keye.router.main.R;
import com.keye.router.main.chart.ui.SheetFragment.OnListFragmentInteractionListener;
import com.keye.router.main.databinding.ConmonSheetItemListBinding;
import com.keye.router.main.excel.bean.IndexFundsBean;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class SheetsAdapter extends RecyclerView.Adapter<SheetsAdapter.BaseSheetHolder> {

    private final List<IndexFundsBean> mValues = new ArrayList<>();
    private final OnListFragmentInteractionListener mListener;

    private static final int SHEET_HEADER_TYPE = 1;//顶部header类型
    private static final int headerCount = 1;

    public SheetsAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    public void setData(List<IndexFundsBean> data) {
        if (data != null) {
            mValues.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return SHEET_HEADER_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseSheetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConmonSheetItemListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.conmon_sheet_item_list, parent, false);
        BaseSheetHolder holder;
        if (viewType == SHEET_HEADER_TYPE) {
            holder = new SheetHeaderViewHolder(binding);
        }else {
            holder = new SheetItemViewHolder(binding);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final BaseSheetHolder holder, final int position) {
        if (holder instanceof SheetHeaderViewHolder) {
            ((SheetHeaderViewHolder) holder).initHeader();
        } else if (holder instanceof SheetItemViewHolder) {
            holder.mItem = mValues.get(position);
            ((SheetItemViewHolder) holder).getBinding().setIndexFundsBean(holder.mItem);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(position, holder.mItem);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() + headerCount;
    }

    public abstract class BaseSheetHolder<T> extends RecyclerView.ViewHolder {
        private final View mView;
        private IndexFundsBean mItem;

        public BaseSheetHolder(View view) {
            super(view);
            mView = view;
        }

        public abstract T getBinding();
    }

    public class SheetItemViewHolder extends BaseSheetHolder<ConmonSheetItemListBinding> {
        private ConmonSheetItemListBinding binding;


        public SheetItemViewHolder(ConmonSheetItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public ConmonSheetItemListBinding getBinding() {
            return binding;
        }
    }

    public class SheetHeaderViewHolder extends BaseSheetHolder<ConmonSheetItemListBinding> {
        private ConmonSheetItemListBinding binding;

        public SheetHeaderViewHolder(ConmonSheetItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public ConmonSheetItemListBinding getBinding() {
            return binding;
        }

        public void initHeader() {
            if(!TextUtils.isEmpty(binding.date.getText())){
                return;
            }
            binding.date.setText("日期");
            binding.closePrice.setText("收盘价");
            binding.pe.setText("市盈率");
            binding.pb.setText("市净率");
            binding.roe.setText("净资产收益率");
            binding.eps.setText("每股收益");
            binding.netAssets.setText("每股净资产");
        }
    }
}
