package com.mybringback.thebasics.trade.graphs;


import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mybringback.thebasics.trade.JSONmodel.Dataset;
import com.mybringback.thebasics.trade.mainActivityFragments.StocksFragment;
import com.mybringback.thebasics.trade.R;
import com.mybringback.thebasics.trade.RealmModel.DataSave;
import com.mybringback.thebasics.trade.graphsFragments.stocks.HalfYearFragment;
import com.mybringback.thebasics.trade.graphsFragments.stocks.MonthFragment;
import com.mybringback.thebasics.trade.graphsFragments.stocks.ThreeDaysFragment;
import com.mybringback.thebasics.trade.graphsFragments.stocks.ThreeMonthsFragment;
import com.mybringback.thebasics.trade.graphsFragments.stocks.WeekFragment;
import com.mybringback.thebasics.trade.graphsFragments.stocks.YearFragment;

import java.util.ArrayList;
import java.util.List;

public class StocksGraphActivity extends AppCompatActivity {

    TextView currentValue, open, close, low, high, change, changeProc;
    float floatOpenTday;
    float floatOpenYday;
    float floatChange;
    int index;
    String Change, ChangeProc;
    TextView name;
    float floatChangeProc;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public DataSave dataSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        Dataset dataSet = getIntent().getParcelableExtra(
                Dataset.class.getCanonicalName());

        open = (TextView)findViewById(R.id.open);
        close = (TextView) findViewById(R.id.close);
        low = (TextView) findViewById(R.id.low);
        high = (TextView)findViewById(R.id.high);
        change = (TextView) findViewById(R.id.change);
        changeProc = (TextView) findViewById(R.id.changeProc);
        name = (TextView) findViewById(R.id.name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        floatOpenTday = Float.parseFloat(StocksFragment.result.getData().get(0).get(1));
        floatOpenYday = Float.parseFloat(StocksFragment.result.getData().get(1).get(1));
        floatChange = floatOpenTday-floatOpenYday;
        Change = String.format("%.2f", floatChange);
        floatChangeProc =  (floatChange/floatOpenYday)*100;
        ChangeProc = String.format("%.2f", floatChangeProc);

        try {
            int index = dataSet.getName().indexOf(')');
            name.setText(dataSet.getName().substring(0, index + 1));
        } catch (NullPointerException e ) {
            /*int index1 = MainActivity.dataSave.getName().indexOf(')');
            name.setText(MainActivity.dataSave.getName().substring(0, index1 + 1));*/
            name.setText(dataSet.getName());
        }
        open.setText("Open: " + StocksFragment.result.getData().get(0).get(1));
        close.setText("Close: " + StocksFragment.result.getData().get(0).get(4));
        high.setText("High: " + StocksFragment.result.getData().get(0).get(2));
        low.setText("Low: " + StocksFragment.result.getData().get(0).get(3));
        change.setText("Change: " + Change);
        changeProc.setText("Change: " + ChangeProc + "%");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ThreeDaysFragment(), "3D");
        adapter.addFragment(new WeekFragment(), "7D");
        adapter.addFragment(new MonthFragment(), "1M");
        adapter.addFragment(new ThreeMonthsFragment(), "3M");
        adapter.addFragment(new HalfYearFragment(), "6M");
        adapter.addFragment(new YearFragment(), "1Y");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
