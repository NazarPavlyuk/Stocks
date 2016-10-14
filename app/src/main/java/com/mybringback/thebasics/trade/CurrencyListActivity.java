package com.mybringback.thebasics.trade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mybringback.thebasics.trade.JSONmodelForCurrencies.CurrencyListModel;
import com.mybringback.thebasics.trade.adapter.CurrencyListAdapter;
import com.mybringback.thebasics.trade.adapter.DividerItemDecoration;
import com.mybringback.thebasics.trade.adapter.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CurrencyListActivity extends AppCompatActivity {

    public List<CurrencyListModel> currenciesDataListMain = new ArrayList<>();
    private RecyclerView recyclerView;
    private CurrencyListAdapter mAdapter;
    Context context;
    public static CurrencyListModel currencyResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);

        mAdapter = new CurrencyListAdapter(getApplicationContext(),currenciesDataListMain);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);

        prepareCurrencyData();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        currencyResult =  currenciesDataListMain.get(position);
                        Intent intent = new Intent();
                        intent.putExtra(CurrencyListModel.class.getCanonicalName(), currencyResult);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                    @Override
                    public void onLongItemClick(View view, final int position) {
                    }

                })
        );
    }

    private void prepareCurrencyData() {
        CurrencyListModel currency = new CurrencyListModel("USD","United States Dollar");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("EUR", "Europe Euro");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("JPY","Japan Yen");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("GBP", "United Kingdom Pound");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("AUD", "Australian Dollar");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("CNY", "China Yuan");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("CHF", "Switzerland Franc");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("BRL", "Brazil Real");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("BGN", "Bulgaria Lev");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("CAD", "Canada Dollar");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("HRK", "Croatia Kuna");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("CZK", "Czech Republic Krona");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("DKK", "Denmark Krone");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("HKD", "Hong Kong SAR Dollar");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("HUF", "Hungary Forint");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("INR", "India Rupee");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("IDR", "Indonesia Rupiah");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("ILS", "Israel Shekel");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("KRW", "Korea Won");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("MYR", "Malaysia Ringgit");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("MXN", "Mexico Peso");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("NZD", "New Zealand Dollar");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("NOK", "Norway Krone");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("PHP" , "Philippines Peso");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("PLN", "Poland Zloty");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("RON", "Romania Leu");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("RUB", "Russia Rouble");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("SGD", "Singhapore Dollar");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("ZAR", "South Africa Rand");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("SEK", "Sweden Krona");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("THB", "Thailand Bath");
        currenciesDataListMain.add(currency);

        currency = new CurrencyListModel("TRY", "Turkey Lira");
        currenciesDataListMain.add(currency);

        mAdapter.notifyDataSetChanged();
    }
}
