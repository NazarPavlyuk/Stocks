package com.mybringback.thebasics.trade.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mybringback.thebasics.trade.JSONmodel.Dataset;
import com.mybringback.thebasics.trade.R;

import java.util.List;

/**
 * Created by ALLO on 19.09.2016.
 */
public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.MyViewHolder> {

    private List<Dataset> stocksDataListResource;

    float floatChange;
    String Change, ChangeProc;
    float floatChangeProc;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView resource_symbol, current_date, current_value, current_absolute_change, current_relative_change;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            resource_symbol = (TextView) view.findViewById(R.id.stocks_symbol);
            current_date = (TextView) view.findViewById(R.id.current_date);
            current_value = (TextView) view.findViewById(R.id.current_value);

            current_absolute_change = (TextView) view.findViewById(R.id.current_absolute_change);
            current_relative_change = (TextView) view.findViewById(R.id.current_relative_change);

            relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);

        }
    }

    public ResourceAdapter(Context context, List<Dataset> stocksDataListResource) {
        this.context=context;
        this.stocksDataListResource = stocksDataListResource;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Dataset resources = stocksDataListResource.get(position);
        holder.resource_symbol.setText(resources.getDataset_code());
        holder.current_date.setText(resources.getData().get(0).get(0));
        holder.current_value.setText(resources.getData().get(0).get(1));
        if (Float.parseFloat(resources.getData().get(0).get(1))
                < Float.parseFloat(resources.getData().get(1).get(1))) {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorChangeNegative));
        } else if (Float.parseFloat(resources.getData().get(0).get(1))
                > Float.parseFloat(resources.getData().get(1).get(1))) {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorChangePositive));
        } else if (Float.parseFloat(resources.getData().get(0).get(1))
                == Float.parseFloat(resources.getData().get(1).get(1))) {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorNoChange));
        }
        floatChange = Float.parseFloat(resources.getData().get(0).get(1))-Float.parseFloat(resources.getData().get(1).get(1));
        Change = String.format("%.2f", floatChange);
        floatChangeProc =  (floatChange/Float.parseFloat(resources.getData().get(1).get(1)))*100;
        ChangeProc = String.format("%.2f", floatChangeProc);
        holder.current_absolute_change.setText(Change);
        holder.current_relative_change.setText(ChangeProc + "%");
    }
    @Override
    public int getItemCount() {
        return stocksDataListResource.size();
    }
}
