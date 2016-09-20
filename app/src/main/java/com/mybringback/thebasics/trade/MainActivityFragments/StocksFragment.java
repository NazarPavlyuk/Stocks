package com.mybringback.thebasics.trade.MainActivityFragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mybringback.thebasics.trade.JSONmodel.DataService;
import com.mybringback.thebasics.trade.JSONmodel.Dataset;
import com.mybringback.thebasics.trade.JSONmodel.Main;
import com.mybringback.thebasics.trade.MainActivity;
import com.mybringback.thebasics.trade.R;
import com.mybringback.thebasics.trade.RealmModel.DataSave;
import com.mybringback.thebasics.trade.StocksActivity;
import com.mybringback.thebasics.trade.adapter.RecyclerItemClickListener;
import com.mybringback.thebasics.trade.adapter.StocksAdapter;

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

/**
 * Created by ALLO on 19.09.2016.
 */
public class StocksFragment extends Fragment {

    /*private List<Dataset> stocksDataListMain = new ArrayList<>();
    public static Dataset result;
    private RecyclerView recyclerView;
    private StocksAdapter mAdapter;
    Context context;
    Realm realm;
    ProgressDialog dialog;
    private SearchView searchView;
    private MenuItem searchItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stocks, container, false);
        final RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).build();
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
            stocksDataListMain.add(new Dataset(secondlyAddedElements, element.getDataset_code(), element.getName()));
        }

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchItem.setVisible(true);
                searchView.setVisibility(View.VISIBLE);
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mAdapter = new StocksAdapter(getContext(),stocksDataListMain);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
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
                        Intent intent = new Intent(getActivity(), StocksActivity.class);
                        intent.putExtra(Dataset.class.getCanonicalName(), result);
                        startActivity(intent);
                    }
                    @Override
                    public void onLongItemClick(View view, final int position) {
                    }

                })
        );

    return rootView;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog = new ProgressDialog( getActivity());
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
                            dataSave.setName(res.getName());
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
                            Toast.makeText( getActivity(), "No matches found. Try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
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
                System.out.println("tap");
                return false;
            }
        });
    }


*/

}
