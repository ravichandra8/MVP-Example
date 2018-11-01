package com.jdsports.com.mysample.modal;

import com.jdsports.com.mysample.modal.pojo.IMResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageServices {

    @GET("api/")
    Call<IMResponse> getAllImages(@Query("key") String apiKey);

    @GET("api/")
    Call<IMResponse> searchImages(@Query("key") String apiKey, @Query("q") String searchQuery);

}
