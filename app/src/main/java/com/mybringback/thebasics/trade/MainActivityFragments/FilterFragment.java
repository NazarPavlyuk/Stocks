package com.mybringback.thebasics.trade.mainActivityFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mybringback.thebasics.trade.R;

/**
 * Created by ALLO on 21.09.2016.
 */
public class FilterFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setHasOptionsMenu(true);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        TextView text = (TextView) rootView.findViewById(R.id.text2);
        return rootView;
    }
}
