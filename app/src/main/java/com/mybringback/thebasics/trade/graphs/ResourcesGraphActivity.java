package com.mybringback.thebasics.trade.graphs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mybringback.thebasics.trade.JSONmodel.Dataset;
import com.mybringback.thebasics.trade.mainActivityFragments.ResourcesFragment;
import com.mybringback.thebasics.trade.R;
import com.mybringback.thebasics.trade.RealmModel.DataSave;
import com.mybringback.thebasics.trade.graphsFragments.resources.HalfYearResourcesFragment;
import com.mybringback.thebasics.trade.graphsFragments.resources.MonthResourcesFragment;
import com.mybringback.thebasics.trade.graphsFragments.resources.ThreeDaysResourcesFragment;
import com.mybringback.thebasics.trade.graphsFragments.resources.ThreeMonthsResourcesFragment;
import com.mybringback.thebasics.trade.graphsFragments.resources.WeekResourcesFragment;
import com.mybringback.thebasics.trade.graphsFragments.resources.YearResourcesFragment;

import java.util.ArrayList;
import java.util.List;

public class ResourcesGraphActivity extends AppCompatActivity {


    TextView todayValue, change, changeProc;
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
        setContentView(R.layout.activity_resources);

        Dataset dataSet = getIntent().getParcelableExtra(
                Dataset.class.getCanonicalName());

        change = (TextView) findViewById(R.id.change_resources);
        changeProc = (TextView) findViewById(R.id.changeProc_resources);
        name = (TextView) findViewById(R.id.name_resources);
        todayValue = (TextView) findViewById(R.id.today_value);

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        floatOpenTday = Float.parseFloat(ResourcesFragment.result1.getData().get(0).get(1));
        floatOpenYday = Float.parseFloat(ResourcesFragment.result1.getData().get(1).get(1));
        floatChange = floatOpenTday-floatOpenYday;
        Change = String.format("%.2f", floatChange);
        floatChangeProc =  (floatChange/floatOpenYday)*100;
        ChangeProc = String.format("%.2f", floatChangeProc);

        try {
            name.setText(dataSet.getName());
        } catch (NullPointerException e ) {
            name.setText(dataSet.getName());
        }

        todayValue.setText("Current Value:" + ResourcesFragment.result1.getData().get(0).get(1));
        change.setText("Change: " + Change);
        changeProc.setText("Change: " + ChangeProc + "%");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ThreeDaysResourcesFragment(), "3D");
        adapter.addFragment(new WeekResourcesFragment(), "7D");
        adapter.addFragment(new MonthResourcesFragment(), "1M");
        adapter.addFragment(new ThreeMonthsResourcesFragment(), "3M");
        adapter.addFragment(new HalfYearResourcesFragment(), "6M");
        adapter.addFragment(new YearResourcesFragment(), "1Y");
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
