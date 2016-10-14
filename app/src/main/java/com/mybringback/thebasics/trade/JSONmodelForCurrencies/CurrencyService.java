package com.mybringback.thebasics.trade.JSONmodelForCurrencies;

import com.mybringback.thebasics.trade.JSONmodel.Main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ALLO on 28.09.2016.
 */

public interface CurrencyService {
    @GET("datasets/ECB/{item}.json")
    Call<Main> searchItem(@Path("item") String item, @Query("api_key") String api_key);
}
