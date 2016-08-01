package com.mybringback.thebasics.trade.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ALLO on 21.07.2016.
 */
public interface DataService {
    @GET("datasets/WIKI/{item}.json")
    Call<Main> searchItem(@Path("item") String item);

}
