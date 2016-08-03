package com.mybringback.thebasics.trade.search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mybringback.thebasics.trade.MainActivity;
import com.mybringback.thebasics.trade.R;
import com.mybringback.thebasics.trade.model.DataService;
import com.mybringback.thebasics.trade.model.Dataset;
import com.mybringback.thebasics.trade.model.Main;
import com.mybringback.thebasics.trade.recycler.RecyclerItemClickListener;
import com.mybringback.thebasics.trade.recycler.StocksAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HandleActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String ARG_EXAMPLE_NAME = "name";
    private List<Dataset> stocksDataList = new ArrayList<>();



    private RecyclerView recyclerView;
    private StocksAdapter mAdapter;
    Context context;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new StocksAdapter(getApplicationContext(),stocksDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        dialog = new ProgressDialog(HandleActivity.this);
                        dialog.setMessage("Sending");
                        dialog.show();
                        // do whatever
                        Intent intent = new Intent();
                        // Add the required data to be sent to the first activity
                        /*intent.putExtra(ARG_EXAMPLE_NAME,  stocksDataList.get(position));*/
                        MainActivity.result=stocksDataList.get(position);

                        // Set the result code, send back the intent with the data to be managed by the first activity
                        setResult(Activity.RESULT_OK, intent);
                        finish(); // Finish the second activity
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }


    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem backItem = menu.findItem(R.id.action_back);
        backItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(HandleActivity.this, MainActivity.class));
                return false;
            }
        });
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        dialog = new ProgressDialog(HandleActivity.this);
        dialog.setMessage("Searching");
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.quandl.com/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DataService service = retrofit.create(DataService.class);
        final Call<Main> call =
                service.searchItem(query);

        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call <Main> call, Response<Main> response) {
                Log.e("onResponse", String.valueOf(response.raw()));
                Log.e("TAG", response.body().toString());
                stocksDataList.add(response.body().getDataset());
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call< Main> call, Throwable t) {
                Log.e("onFail", t.getMessage());
            }
        });
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }



}
