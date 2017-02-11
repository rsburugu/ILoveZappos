package com.zappos.consumer.services;


import com.zappos.consumer.services.models.ProductsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ramya on 9/2/17.
 */
public interface ZapposAPIService {

    @GET("/Search")
    Call<ProductsResponse> getSearchResult(@Query("term") String search,  @Query("key") String key);

}
