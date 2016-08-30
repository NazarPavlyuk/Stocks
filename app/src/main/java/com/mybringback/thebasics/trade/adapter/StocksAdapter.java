package com.mybringback.thebasics.trade.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mybringback.thebasics.trade.R;
import com.mybringback.thebasics.trade.JSONmodel.Dataset;

import java.util.List;

/**
 * Created by ALLO on 23.07.2016.
 */
public class StocksAdapter extends RecyclerView.Adapter<StocksAdapter.MyViewHolder> {

    private List<Dataset> stocksDataList;

    float floatChange;
    String Change, ChangeProc;
    float floatChangeProc;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView stock_symbol, current_date, current_value, current_absolute_change, current_relative_change;
        public RelativeLayout relativeLayout;
        public FloatingActionButton fabClear;

        public MyViewHolder(View view) {
            super(view);
            stock_symbol = (TextView) view.findViewById(R.id.stocks_symbol);
            current_date = (TextView) view.findViewById(R.id.current_date);
            current_value = (TextView) view.findViewById(R.id.current_value);

            current_absolute_change = (TextView) view.findViewById(R.id.current_absolute_change);
            current_relative_change = (TextView) view.findViewById(R.id.current_relative_change);

            relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);

            /*fabClear = (FloatingActionButton) view.findViewById(R.id.fab_clear);*/
        }
    }

    public StocksAdapter(Context context,List<Dataset> stocksDataList) {
        this.context=context;
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
        holder.stock_symbol.setText(stocks.getDataset_code());
        holder.current_date.setText(stocks.getData().get(0).get(0));
        holder.current_value.setText(stocks.getData().get(0).get(1));
        if (Float.parseFloat(stocks.getData().get(0).get(1))
                < Float.parseFloat(stocks.getData().get(1).get(1))) {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorChangeNegative));
        } else if (Float.parseFloat(stocks.getData().get(0).get(1))
                > Float.parseFloat(stocks.getData().get(1).get(1))) {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorChangePositive));
        } else if (Float.parseFloat(stocks.getData().get(0).get(1))
                == Float.parseFloat(stocks.getData().get(1).get(1))) {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorNoChange));
        }
        floatChange = Float.parseFloat(stocks.getData().get(0).get(1))-Float.parseFloat(stocks.getData().get(1).get(1));
        Change = String.format("%.2f", floatChange);
        floatChangeProc =  (floatChange/Float.parseFloat(stocks.getData().get(1).get(1)))*100;
        ChangeProc = String.format("%.2f", floatChangeProc);
        holder.current_absolute_change.setText(Change);
        holder.current_relative_change.setText(ChangeProc + "%");
    }
    @Override
    public int getItemCount() {
        return stocksDataList.size();
    }
}
