package com.keye.router.main.chart.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keye.router.main.R;
import com.keye.router.main.chart.ui.SheetFragment.OnListFragmentInteractionListener;
import com.keye.router.main.excel.bean.IndexFundsBean;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class SheetsAdapter extends RecyclerView.Adapter<SheetsAdapter.ViewHolder> {

    private final List<IndexFundsBean> mValues = new ArrayList<>();
    private final OnListFragmentInteractionListener mListener;

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
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conmon_sheet_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.mItem = mValues.get(position);
        holder.mIdView.setText("id");
        holder.mContentView.setText("haha");

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

    @Override
    public int getItemCount() {
        return mValues.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public IndexFundsBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
