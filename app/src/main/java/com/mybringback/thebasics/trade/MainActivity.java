package com.mybringback.thebasics.trade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/*import com.facebook.login.LoginManager;*/
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.mybringback.thebasics.trade.JSONmodel.Dataset;
import com.mybringback.thebasics.trade.JSONmodelForCurrencies.CurrencyListModel;
import com.mybringback.thebasics.trade.JSONmodelForCurrencies.CurrencyParceModel;
import com.mybringback.thebasics.trade.mainActivityFragments.CurrencyCalculatorFragment;
import com.mybringback.thebasics.trade.mainActivityFragments.CurrencyFragment;
import com.mybringback.thebasics.trade.mainActivityFragments.FilterFragment;
import com.mybringback.thebasics.trade.mainActivityFragments.ResourcesFragment;
import com.mybringback.thebasics.trade.mainActivityFragments.StocksFragment;
import com.mybringback.thebasics.trade.login.LoginActivity;
import com.mybringback.thebasics.trade.adapter.StocksAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener/*,SearchView.OnQueryTextListener*/ {

    private static final int REQUEST_EXAMPLE = 0;
    private FirebaseAuth mAuth;
    private List<Dataset> stocksDataListMain = new ArrayList<>();
    public static CurrencyListModel currency;
    private RecyclerView recyclerView;
    private StocksAdapter mAdapter;
    private Realm realm;
    Context context;
    ProgressDialog dialog;
    Menu menu;
    private SearchView searchView;
    private MenuItem searchItem;
    private CurrencyListModel parcelatedFirst;
    private CurrencyParceModel parcelatedSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = LoginActivity.Singleton.instance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = StocksFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                break;
            case R.id.action_filter:
                break;
            case R.id.action_sign_out:
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.nav_stocks) {
            fragmentClass = StocksFragment.class;
        } else if (id == R.id.nav_resources) {
            fragmentClass = ResourcesFragment.class;
        } else if (id == R.id.nav_currencies) {
            fragmentClass = CurrencyFragment.class;
        } else if (id == R.id.nav_currencies_calculator) {
            fragmentClass = CurrencyCalculatorFragment.class;
        } else if (id == R.id.nav_share) {
            fragmentClass = FilterFragment.class;
        } else if (id == R.id.nav_send) {
            fragmentClass = ResourcesFragment.class;
            mAuth.signOut();
            if (FacebookSdk.isInitialized()){
                LoginManager.getInstance().logOut();
            }
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                // resultCode is set by the second activity
                if (resultCode == Activity.RESULT_OK) {
                    // Get our result from the data Intent and display it
                    Log.e("TAG", "result is");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flContent, new CurrencyCalculatorFragment()).commit();
                    *//*currency = data.getParcelableExtra(CurrencyListModel.class.getCanonicalName())*//*;
                }
                break;
            }
        }
    }*/

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                /*FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.flContent, new CurrencyCalculatorFragment()).commit();*/
                parcelatedFirst = data.getParcelableExtra(CurrencyListModel.class.getCanonicalName());
                /*CurrencyListModel currencyListModel = data.getParcelableExtra(CurrencyListModel.class.getCanonicalName());
                Bundle bundle = new Bundle();
                bundle.putParcelable("sexy", currencyListModel );
                CurrencyCalculatorFragment fragobj = new CurrencyCalculatorFragment();
                fragobj.setArguments(bundle);*/
                /*FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, new CurrencyCalculatorFragment()).commit();*/
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }

        }
       if (requestCode == 2) {
           if (resultCode == Activity.RESULT_OK) {
               CurrencyListModel parcelated = data.getParcelableExtra(CurrencyListModel.class.getCanonicalName());
               String parcelatedName =  parcelated.getName();
               String parcelatedSource = parcelated.getSource();
               CurrencyParceModel currencyModel = new CurrencyParceModel(parcelatedSource, parcelatedName);
               parcelatedSecond = currencyModel;
           }
       }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(parcelatedFirst!=null) {
            EventBus.getDefault().post(parcelatedFirst);
        }
        if(parcelatedSecond!=null) {
            EventBus.getDefault().post(parcelatedSecond);
        }
    }
}