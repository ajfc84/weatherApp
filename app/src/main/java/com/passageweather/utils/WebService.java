package com.passageweather.utils;

import android.graphics.Bitmap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WebService {

    @GET("/{region}/{variable}")
    Call<String> getMapJSON(@Path("region") String region, @Path("variable") String variable);

}
