package com.mybringback.thebasics.trade.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mybringback.thebasics.trade.JSONmodelForCurrencies.CurrencyListModel;
import com.mybringback.thebasics.trade.R;

import java.util.List;

/**
 * Created by ALLO on 27.09.2016.
 */

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.MyViewHolder> {

    private List<CurrencyListModel> currenciesDataList;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView currency_name;

        public MyViewHolder(View view) {
            super(view);
            currency_name = (TextView) view.findViewById(R.id.currency_name);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_list_row, parent, false);
        return new CurrencyListAdapter.MyViewHolder(itemView);
    }

    public CurrencyListAdapter(Context context, List<CurrencyListModel> currenciesDataList) {
        this.context=context;
        this.currenciesDataList = currenciesDataList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CurrencyListModel currencies = currenciesDataList.get(position);
        holder.currency_name.setText(currencies.getSource());
    }

    @Override
    public int getItemCount() {
        return currenciesDataList.size();
    }
}
