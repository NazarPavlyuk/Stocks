package com.mybringback.thebasics.trade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.mybringback.thebasics.trade.login.LoginActivity;
import com.mybringback.thebasics.trade.model.Dataset;
import com.mybringback.thebasics.trade.model.Main;
import com.mybringback.thebasics.trade.recycler.RecyclerItemClickListener;
import com.mybringback.thebasics.trade.recycler.StocksAdapter;
import com.mybringback.thebasics.trade.search.HandleActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_EXAMPLE = 0;
    private FirebaseAuth mAuth;
    private List<Dataset> stocksDataListMain = new ArrayList<>();
    public static Dataset result;
    private RecyclerView recyclerView;
    private StocksAdapter mAdapter;
    FloatingActionButton fabClear;
   /* private Realm realm;*/

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

        mAuth = LoginActivity.Singleton.instance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, HandleActivity.class);
                startActivityForResult(i, 1);
            }
        });

        /*fabClear = (FloatingActionButton) findViewById(R.id.fab_clear);*/
        /*fabClear.setVisibility(View.GONE);*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.deleteRealm(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);*/

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new StocksAdapter(getApplicationContext(),stocksDataListMain);
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
                mAdapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        startActivity(new Intent(MainActivity.this, StocksActivity.class));

                    }
                    @Override
                    public void onLongItemClick(View view, final int position) {
                        /*fabClear.setVisibility(View.VISIBLE);*/
                        /*fabClear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                stocksDataListMain.remove(position);
                                mAdapter.notifyItemRemoved(position);
                            }
                        });*/
                        /*stocksDataListMain.remove(position);
                        mAdapter.notifyItemRemoved(position);*/
                    }

                })
        );
        
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                // resultCode is set by the second activity
                if (resultCode == Activity.RESULT_OK) {
                    // Get our result from the data Intent and display it
                    /*result = data.getParcelableExtra("name");*/
                    Log.e("TAG", "result: " + result);
                    /*Toast.makeText(this, "Result: " + result, Toast.LENGTH_LONG).show();*/
                    stocksDataListMain.add(result);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            }
        }
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
}
