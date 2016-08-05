package com.mybringback.thebasics.trade;


import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mybringback.thebasics.trade.fragments.HalfYearFragment;
import com.mybringback.thebasics.trade.fragments.MonthFragment;
import com.mybringback.thebasics.trade.fragments.ThreeDaysFragment;
import com.mybringback.thebasics.trade.fragments.ThreeMonthsFragment;
import com.mybringback.thebasics.trade.fragments.WeekFragment;
import com.mybringback.thebasics.trade.fragments.YearFragment;

import java.util.ArrayList;
import java.util.List;

public class StocksActivity extends AppCompatActivity {

    TextView open, close, low, high, change, changeProc;
    float floatOpenTday;
    float floatOpenYday;
    float floatChange;
    String Change, ChangeProc;
    float floatChangeProc;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        open = (TextView)findViewById(R.id.open);
        close = (TextView) findViewById(R.id.close);
        low = (TextView) findViewById(R.id.low);
        high = (TextView)findViewById(R.id.high);
        change = (TextView) findViewById(R.id.change);
        changeProc = (TextView) findViewById(R.id.changeProc);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        floatOpenTday = Float.parseFloat(MainActivity.result.getData().get(0).get(1));
        floatOpenYday = Float.parseFloat(MainActivity.result.getData().get(1).get(1));
        floatChange = floatOpenTday-floatOpenYday;
        Change = String.format("%.2f", floatChange);
        floatChangeProc =  (floatChange/floatOpenYday)*100;
        ChangeProc = String.format("%.2f", floatChangeProc);



        open.setText("Open: " + MainActivity.result.getData().get(0).get(1));
        close.setText("Close: " + MainActivity.result.getData().get(0).get(4));
        high.setText("High: " + MainActivity.result.getData().get(0).get(2));
        low.setText("Low: " + MainActivity.result.getData().get(0).get(3));
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
