package com.mybringback.thebasics.trade.mainActivityFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mybringback.thebasics.trade.CurrencyListActivity;
import com.mybringback.thebasics.trade.R;

/**
 * Created by ALLO on 19.09.2016.
 */
public class CurrencyFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_currency, container, false);
        /*TextView text = (TextView) rootView.findViewById(R.id.text);*/
        Button currencyList1 = (Button) rootView.findViewById(R.id.buttonStart1);
        Button currencyList2 = (Button) rootView.findViewById(R.id.buttonStart2);
        currencyList1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CurrencyListActivity.class);
                getActivity().startActivityForResult(intent, 1);
            }
        });

        currencyList2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CurrencyListActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        return rootView;
    }
}
