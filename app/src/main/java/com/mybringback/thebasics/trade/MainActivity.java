package com.mybringback.thebasics.trade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/*import com.facebook.login.LoginManager;*/
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.mybringback.thebasics.trade.JSONmodel.DataService;
import com.mybringback.thebasics.trade.JSONmodel.Dataset;
import com.mybringback.thebasics.trade.JSONmodel.Main;
import com.mybringback.thebasics.trade.RealmModel.DataSave;
import com.mybringback.thebasics.trade.login.LoginActivity;
import com.mybringback.thebasics.trade.adapter.RecyclerItemClickListener;
import com.mybringback.thebasics.trade.adapter.StocksAdapter;
import com.mybringback.thebasics.trade.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener {

    private static final int REQUEST_EXAMPLE = 0;
    private FirebaseAuth mAuth;
    private List<Dataset> stocksDataListMain = new ArrayList<>();
    /*private RealmList<DataSavior> stocksDataListBase = new RealmList<>();*/
    public static Dataset result;
    public static Dataset results;
    private RecyclerView recyclerView;
    private StocksAdapter mAdapter;
    private Realm realm;
    Context context;
    ProgressDialog dialog;
    Menu menu;
    private SearchView searchView;
    private MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();

        final RealmResults<DataSave> elements = realm.where(DataSave.class).findAll();


        for(DataSave element:elements){
            String blockDividedBySemicolon = element.getData();
            String[] getBlockDividedByComa = blockDividedBySemicolon.split(";");
            List<List<String>> secondlyAddedElements = new ArrayList<>();
            for (int j = 0; j < getBlockDividedByComa.length; j++) {
                List<String> firstlyAddedElements = new ArrayList<>();
                String[] unit = getBlockDividedByComa[j].split(",");
                for (int i = 0; i < unit.length; i++) {
                    firstlyAddedElements.add(unit[i]);
                }
                secondlyAddedElements.add(firstlyAddedElements);
            }
            stocksDataListMain.add(new Dataset(secondlyAddedElements, element.getDataset_code()));
        }

        /*searchItem.setVisible(false);
        searchView.setVisibility(View.GONE);*/

        mAuth = LoginActivity.Singleton.instance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(i, 1);*/
                /*menu.findItem(R.id.action_search).setVisible(true);*/
                searchItem.setVisible(true);
                searchView.setVisibility(View.VISIBLE);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new StocksAdapter(getApplicationContext(),stocksDataListMain);
        /*rAdapter = new RealmAdapter(getApplicationContext(),stocksDataListBase);*/

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
                stocksDataListMain.remove(viewHolder.getAdapterPosition());
                try {
                    final DataSave element = realm.where(DataSave.class).equalTo("dataset_code", stocksDataListMain.get(viewHolder.getAdapterPosition()).getDataset_code()).findFirst();
                    realm.beginTransaction();
                    element.deleteFromRealm();
                    realm.commitTransaction();
                } catch (IndexOutOfBoundsException e){
                    realm.beginTransaction();
                    realm.deleteAll();
                    realm.commitTransaction();
                }
                mAdapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        result = stocksDataListMain.get(position);
                        startActivity(new Intent(MainActivity.this, StocksActivity.class));
                        finish();
                    }
                    @Override
                    public void onLongItemClick(View view, final int position) {
                    }

                })
        );

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchItem.setVisible(false);
        searchView.setVisibility(View.GONE);
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Searching");
        dialog.show();
        if(query.equals(query.toLowerCase())){
            query = query.toUpperCase();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.quandl.com/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DataService service = retrofit.create(DataService.class);
        final Call<Main> call =
                service.searchItem(query, "365", "desc");

        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call <Main> call, Response<Main> response) {
                Log.e("onResponse", String.valueOf(response.raw()));
                try {
                    Log.e("TAG", response.body().toString());
                    Dataset res = response.body().getDataset();
                    final DataSave dataSave = new DataSave();
                    dataSave.setDataset_code(res.getDataset_code());
                    String blockDividedBySemicolon = "";
                    for(int j = 0; j<res.getData().size(); j++){
                        String makeBlockDividedByComa = "";
                        for (int i = 0; i < res.getData().get(j).size(); i++) {
                            String element;
                            element = res.getData().get(j).get(i);
                            makeBlockDividedByComa = makeBlockDividedByComa.concat(element+",");
                        }
                        makeBlockDividedByComa = makeBlockDividedByComa.substring(0, makeBlockDividedByComa.length()-1);
                        blockDividedBySemicolon= blockDividedBySemicolon.concat(makeBlockDividedByComa+";");
                    }
                    blockDividedBySemicolon= blockDividedBySemicolon.substring(0, blockDividedBySemicolon.length()-1);
                    dataSave.setData(blockDividedBySemicolon);
                    realm.beginTransaction();
                    realm.copyToRealm(dataSave);
                    realm.commitTransaction();
                    stocksDataListMain.add(response.body().getDataset());
                    mAdapter.notifyDataSetChanged();
                } catch (NullPointerException e){
                    Log.e("onFail", e.toString());
                    Toast.makeText(MainActivity.this, "No matches found. Try again.",
                            Toast.LENGTH_SHORT).show();
                }
                /*stocksDataListMain.add(results);*/
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call< Main> call, Throwable t) {
                Log.e("onFail", t.getMessage());
            }
        });
        searchView.clearFocus();
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void onDestroy() {
        super.onDestroy();
        realm.close();
        finish();
    }

    public Realm getRealm() {
        return realm;
    }
}
