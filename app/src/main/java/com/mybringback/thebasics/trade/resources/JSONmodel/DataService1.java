package com.mybringback.thebasics.trade.resources.JSONmodel;

import com.mybringback.thebasics.trade.JSONmodel.Main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ALLO on 19.09.2016.
 */
public interface DataService1 {
    @GET("datasets/COM/{item}.json")
    Call<Main> searchItem(@Path("item") String item, @Query("rows") String rows, @Query("order") String order);

}
