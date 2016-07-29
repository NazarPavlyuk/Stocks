package com.mybringback.thebasics.trade.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mybringback.thebasics.trade.R;
import com.mybringback.thebasics.trade.model.Dataset;
import com.mybringback.thebasics.trade.model.Main;

import java.util.List;

/**
 * Created by ALLO on 23.07.2016.
 */
public class StocksAdapter extends RecyclerView.Adapter<StocksAdapter.MyViewHolder> {
    private List<Dataset> stocksDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView stock_symbol, current_date, current_value;

        public MyViewHolder(View view) {
            super(view);
            stock_symbol = (TextView) view.findViewById(R.id.stocks_symbol);
            current_date = (TextView) view.findViewById(R.id.current_date);
            current_value = (TextView) view.findViewById(R.id.current_value);
        }
    }


    public StocksAdapter(List<Dataset> stocksDataList) {
        this.stocksDataList = stocksDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Dataset stocks = stocksDataList.get(position);
        /*holder.stock_symbol.setText( stocks.getDataset_code());*/
       /* holder.current_date.setText( stocks.getDate());
        holder.current_value.setText(stocks.getValue());*/
        holder.stock_symbol.setText( stocks.getDataset_code());
        holder.current_date.setText( stocks.getData().get(0).get(0));
        holder.current_value.setText(stocks.getData().get(0).get(1));
        /*holder.current_date.setText( stocks.getDate());
        holder.current_value.setText(stocks.getValue());*/
    }

    @Override
    public int getItemCount() {
        return stocksDataList.size();
    }
}
