package com.passageweather.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.passageweather.config.MyApp;
import com.passageweather.utils.Constants;
import com.passageweather.utils.MapClient;
import com.passageweather.utils.NetUtils;
import com.passageweather.utils.Utils;
import com.passageweather.utils.WebService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapRepository {
    private WebService ws;
    private OkHttpClient client = new OkHttpClient();

/*
    @Singleton
    @Inject
    public MapRepository(OkHttpClient okHttpClient) {
        client = okHttpClient;
    }
*/

    public MutableLiveData<Bitmap> getLiveForecastMap(URL url) {
        MutableLiveData<Bitmap> data = new MutableLiveData<>();
        RequestQueue queue = MapClient.getInstance(MyApp.getAppContext()).getRequestQueue();
        ImageRequest imageRequest = new ImageRequest(
                url.toString(),
                new com.android.volley.Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        String filename = Uri.parse(url.toString()).getEncodedPath().replace("/", "_").substring(1); // maps_{region}_{variable}_{forecastnumber}.png
                        Utils.saveForecastMap(response, filename);
                        data.postValue(response);
                    }
                },
                800,
                600,
                ImageView.ScaleType.CENTER, // <-
                Bitmap.Config.ARGB_8888,
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, "Unable to retrieve map!", Toast.LENGTH_LONG).show(); // TODO (15) Give user a warning
                        Log.e(this.getClass().getName(), error.getMessage());
                    }
                });
        MapClient.getInstance(MyApp.getAppContext()).addToRequestQueue(imageRequest);
        return data;
    }


    public MutableLiveData<Bitmap> getForecastMap(String region, String variable, String label) {
        MutableLiveData<Bitmap> data = new MutableLiveData<>();
        String relativePath = "maps/" + region + "/" + variable + "/";
        String [] values = label.split("-");
        String forecast = values[1].substring(1, values[1].length() - 4);
        File path = new File(MyApp.getAppContext().getFilesDir(), relativePath);
        File file = new File(path, forecast);
        if(file.exists()) {
            FileInputStream inS = null;
            Bitmap image = null;
            try {
                inS = new FileInputStream(relativePath + forecast + Constants.MAP_EXT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image = BitmapFactory.decodeStream(inS);
            data.setValue(image);
        }
        else {
            URL url = NetUtils.buildMapURL(region, variable, Integer.valueOf(forecast));
            data = getLiveForecastMap(url);
        }
        return data;
    }

/*
    public MutableLiveData<Bitmap> getLiveForecastMap2(URL url) {
        Request request =  new Request.Builder()
                .url(url)
                .build();
        MutableLiveData<Bitmap> data = new MutableLiveData<>();
                client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(MapRepository.class.getName(), e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream stream = response.body().byteStream();
                data.postValue(BitmapFactory.decodeStream(stream));
                Utils.saveForecastMap(data.getValue(), Uri.parse(url.toString()).getLastPathSegment());
            }
        });
        return data;
    }
*/

/*
    public LiveData<List<Bitmap>> getForecastMaps(List<URL> urls) {
        List<Bitmap> bitmapList = new ArrayList<>();
        for(URL u : urls) {
            bitmapList.add(getForecastMap(u.toString()));
        }
        MutableLiveData<List<Bitmap>> list = new MutableLiveData<>();
        list.setValue(bitmapList);
        return list;
    }
*/

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

    public static String [] getForecastMapNamesByRegionAndVariable(String region, String variable) {
        File dir = new File(MyApp.getAppContext().getFilesDir(), NetUtils.buildMapsRelativePath(region, variable));
        return dir.list();
    }

    public static File [] getForecastMapFilesByRegionAndVariable(String region, String variable) {
        File dir = new File(MyApp.getAppContext().getFilesDir(), NetUtils.buildMapsRelativePath(region, variable));
        return dir.listFiles();
    }

}
