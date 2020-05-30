package com.example.radiolocator;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ElevationApi {
    @GET("/maps/api/elevation/json")
    void getElev(
        @Query("locations") String latAndLng,
        @Query("key") String key,
        Callback<Object> cd
    );
}

