package com.passageweather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.passageweather.utils.NetUtils;
import com.passageweather.utils.WeatherUtils;
import com.passageweather.utils.WebService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapRepository {
    private WebService ws;
    OkHttpClient client = new OkHttpClient();

    public Bitmap getForecastMap(URL url) {
        Request request =  new Request.Builder()
                .url(url)
                .build();
        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream stream = null;
        if(response != null) stream = response.body().byteStream();
        return BitmapFactory.decodeStream(stream);
    }

    public LiveData<List<Bitmap>> getForecastMaps(List<URL> urls) {
        List<Bitmap> bitmapList = new ArrayList<>();
        for(URL u : urls) {
            bitmapList.add(getForecastMap(u));
        }
        MutableLiveData<List<Bitmap>> list = new MutableLiveData<>();
        list.setValue(bitmapList);
        return list;
    }

/*
    public LiveData<String> getForecastMapJSON(String region, String variable) {
        final MutableLiveData<String> data = new MutableLiveData<>();
        ws.getMapJSON(region, variable).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ;
            }
        });
        return data;
    }
*/

}
