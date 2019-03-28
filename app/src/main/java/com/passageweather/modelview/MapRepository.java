package com.passageweather.modelview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.passageweather.config.MyApp;
import com.passageweather.model.AppDatabase;
import com.passageweather.model.Map;
import com.passageweather.model.MapDao;
import com.passageweather.utils.Constants;
import com.passageweather.utils.MapClient;
import com.passageweather.utils.NetUtils;
import com.passageweather.utils.Utils;
import com.passageweather.utils.WeatherUtils;
import com.passageweather.utils.WebService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;

public class MapRepository {
    private static MapRepository instance;
    private WebService ws;
    private OkHttpClient client = new OkHttpClient();
    private AppDatabase db;
    private MapDao mapDao;


/*
    @Singleton
    @Inject
    public MapRepository(OkHttpClient okHttpClient) {
        client = okHttpClient;
    }
*/

    private MapRepository(){
        db = AppDatabase.newInstance();
        mapDao = db.mapDao();
    }

    public static synchronized MapRepository newInstance() {
        if(instance == null) instance = new MapRepository();
        return instance;
    }

    public MutableLiveData<Bitmap> getLiveForecastMap(URL url) {
        MutableLiveData<Bitmap> data = new MutableLiveData<>();
        RequestQueue queue = MapClient.getInstance(MyApp.getAppContext()).getRequestQueue();
        ImageRequest imageRequest = new ImageRequest(
                url.toString(),
                new com.android.volley.Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        data.postValue(response);
                        Utils.saveForecastMap(response, url);
                        String [] mapInfo = NetUtils.parseMapsPath(url);
                        Map map = new Map();
                        map.filename = mapInfo[2];
                        map.region = mapInfo[0];
                        map.variable = mapInfo[1];
                        int forecastNumber = Integer.valueOf(map.filename.substring(0, map.filename.length() - 4));
                        map.forecastTime = WeatherUtils.convertForecastNumber2Date(map.variable, forecastNumber);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mapDao.updateMaps(map);
                            }
                        }).start();

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
        final MutableLiveData<Bitmap> data = new MutableLiveData<>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String relativePath = NetUtils.buildMapsRelativePath(region, variable);
                        File path = new File(MyApp.getAppContext().getFilesDir(), relativePath);
                        Map map = mapDao.getMapByRegionAndVariableAndDate(region, variable, label);
                        String forecast = null;
                        File file = null;
                        if (map != null) {//        else forecast = WeatherUtils.convertDate2ForecastNumber(label);
                            forecast = WeatherUtils.convertMapName2ForecastNumber(map.filename);
                            file = new File(path, map.filename);
                            if (file.exists()) {
                                final String forecastNumber = forecast;
                                FileInputStream inS = null;
                                Bitmap image = null;
                                try {
                                    inS = new FileInputStream(relativePath + forecastNumber + Constants.MAP_EXT);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                image = BitmapFactory.decodeStream(inS);
                                data.postValue(image);
                            }
                        }
                    }
                }).start();
/*
            else {
                URL url = NetUtils.buildMapURL(region, variable, Integer.valueOf(forecast));
                data = getLiveForecastMap(url);
            }
*/
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
