package com.passageweather.modelview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.passageweather.R;
import com.passageweather.config.MyApp;
import com.passageweather.model.AppDatabase;
import com.passageweather.model.Map;
import com.passageweather.model.MapDao;
import com.passageweather.model.MapLabel;
import com.passageweather.utils.Constants;
import com.passageweather.utils.MapClient;
import com.passageweather.utils.NetUtils;
import com.passageweather.utils.Utils;
import com.passageweather.utils.WeatherUtils;
import com.passageweather.utils.WebService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;

public class MapRepository {
    private static MapRepository instance;
    private WebService ws;
    private OkHttpClient client = new OkHttpClient();
    private AppDatabase db;
    private MapDao mapDao;
    private ForecastThreadPoolManager mThreadManager;


/*
    @Singleton
    @Inject
    public MapRepository(OkHttpClient okHttpClient) {
        client = okHttpClient;
    }
*/

    static {

    }

    private MapRepository(){
        db = AppDatabase.newInstance();
        mapDao = db.mapDao();
        mThreadManager = ForecastThreadPoolManager.newInstance();
    }

    public static synchronized MapRepository newInstance() {
        if(instance == null) instance = new MapRepository();
        return instance;
    }

    private MutableLiveData<Bitmap> openVolleyConnection(URL url) {
        MutableLiveData<Bitmap> data = new MutableLiveData<>();
        RequestQueue queue = MapClient.getInstance(MyApp.getAppContext()).getRequestQueue();
        ImageRequest imageRequest = new ImageRequest(
                url.toString(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        data.postValue(response);
                    }
                },
                800,
                600,
                ImageView.ScaleType.CENTER, // <-
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, "Unable to retrieve map!", Toast.LENGTH_LONG).show(); // TODO (99) Give user a warning
                        Log.e(this.getClass().getName(), error.getMessage());
                    }
                });
        MapClient.getInstance(MyApp.getAppContext()).addToRequestQueue(imageRequest);
        return data;
    }

    private Bitmap openHttpConnection(URL url) {
        HttpURLConnection conn = null;
        InputStream in = null;
        Bitmap image = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            in = conn.getInputStream();
            image = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public MutableLiveData<Bitmap> getLiveForecastMap(URL url) {
        final MutableLiveData<Bitmap> data = new MutableLiveData<>();
        mThreadManager.addRunnable(new Runnable() {
            @Override
            public void run() {
//                data.obj = openVolleyConnection(url);
                Bitmap image = openHttpConnection(url);
                data.postValue(image);
                String [] mapInfo = NetUtils.parseMapsPath(url);
                Map map = new Map();
                map.onDisk = Utils.saveForecastMap(image, url);
                map.name = mapInfo[2];
                map.region = mapInfo[0];
                map.variable = mapInfo[1];
                int forecastNumber = Integer.valueOf(map.name.substring(0, map.name.length() - 4));
                map.forecastDate = WeatherUtils.convertForecastNumber2Date(map.variable, forecastNumber);
                mapDao.updateMaps(map);
            }
        });
        return data;
    }

    public LiveData<Bitmap> getForecastMap(String region, String variable, int forecastNumber) {
        final MutableLiveData<Bitmap> data = new MutableLiveData<>();
         mThreadManager.addRunnable(new Runnable() {
            @Override
            public void run() {
                String relativePath = NetUtils.buildMapsRelativePath(region, variable);
                File path = new File(MyApp.getAppContext().getFilesDir(), relativePath);
                String forecast = WeatherUtils.convertForecastNumber2MapName(forecastNumber);
                File file = new File(path, forecast + Constants.MAP_EXT);
                if (file.exists()) {
                    FileInputStream inS = null;
                    Bitmap image = null;
                    try {
                        inS = new FileInputStream(relativePath + forecast + Constants.MAP_EXT);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    image = BitmapFactory.decodeStream(inS);
                    data.postValue(image);
                }
                else {
                    URL url = NetUtils.buildMapURL(region, variable, Integer.valueOf(forecast));
                    Bitmap image = openHttpConnection(url);
                    if(image != null) {
                        data.postValue(image);
                        insertMap(image, url);
                    }
                    else {
                        Log.e(MapRepository.class.getName(), "Download image empty");
                        data.postValue(BitmapFactory.decodeResource(MyApp.getAppContext().getResources(), R.drawable.ic_launcher_foreground)); // TODO (65) Need a error image
                    }
                }

            }
        });
        return data;
    }

    private void insertMap(Bitmap image, URL url) {
        String [] mapInfo = NetUtils.parseMapsPath(url);
        Map map = new Map();
        map.onDisk = Utils.saveForecastMap(image, url);
        map.name = mapInfo[2];
        map.region = mapInfo[0];
        map.variable = mapInfo[1];
        int forecastNumber = Integer.valueOf(map.name.substring(0, map.name.length() - 4));
        map.forecastDate = WeatherUtils.convertForecastNumber2Date(map.variable, forecastNumber);
        mapDao.insertMaps(map);
    }

    public MutableLiveData<Bitmap> getForecastMap(String region, String variable, String label) {
        final MutableLiveData<Bitmap> data = new MutableLiveData<>();
        mThreadManager.addRunnable(new Runnable() {
            @Override
            public void run() {
                String relativePath = NetUtils.buildMapsRelativePath(region, variable);
                File path = new File(MyApp.getAppContext().getFilesDir(), relativePath);
                Map map = mapDao.getMapByRegionAndVariableAndDate(region, variable, label);
                File file = new File(path, map.name + Constants.MAP_EXT);
                if (file.exists()) {
                    FileInputStream inS = null;
                    Bitmap image = null;
                    try {
                        inS = new FileInputStream(relativePath + map.name + Constants.MAP_EXT);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    image = BitmapFactory.decodeStream(inS);
                    data.postValue(image);
                }
                else {
                    URL url = NetUtils.buildMapURL(region, variable, Integer.valueOf(map.name));
                    Bitmap image = openHttpConnection(url);
                    if(image != null) {
                        data.postValue(image);
                        map.onDisk = true;
                        mapDao.updateMaps(map);
                    }
                    else {
                        Log.e(this.getClass().getName(), "getForecastMap: Loading image error");
                    }
                }

            }
        });
        return data;
    }

    public void lazyLoadingMode(final String region, final String variable) {
        mThreadManager.addRunnable(new Runnable() {
            @Override
            public void run() {
                int [] forecastHours = WeatherUtils.getForecastHours(variable);
                int [] forecastNumbers = WeatherUtils.getForecastNumbers(variable);
                String [] labels = new String[forecastHours.length];
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM");
                Date date = new Date();
                Map map = null;
                Map [] maps = new Map[forecastHours.length];
                for (int i = 0; i < forecastHours.length; i++) {
                    if(forecastHours[i] == 0 && i > 0) {
                        date.setTime(date.getTime() + 86400000); // add one day
                    }
                    map = new Map();
                    map.onDisk = false;
                    map.region = region;
                    map.variable = variable;
                    map.name = WeatherUtils.convertForecastNumber2MapName(forecastNumbers[i]);
                    map.forecastDate = dateFormat.format(date) + " - " + String.format("%02d00", forecastHours[i]) + " UTC";
                    maps[i] = map;
                }
                mapDao.insertMaps(maps);
            }
        });
    }

    private class Wrapper<T> {
        T [] obj;
    }

    public MapLabel [] getForecastMapLabels(String region, String variable) {
        final Wrapper<MapLabel> data = new Wrapper<>();
        mThreadManager.addRunnable(new Runnable() {
            @Override
            public void run() {
                data.obj = mapDao.getMapForecastDatesByRegionAndVariable(region, variable);
            }
        });
        return data.obj;
    }

    // Todo (98) move this to the thread executor
    public static File [] getForecastMapFilesByRegionAndVariable(String region, String variable) {
        File dir = new File(MyApp.getAppContext().getFilesDir(), NetUtils.buildMapsRelativePath(region, variable));
        return dir.listFiles();
    }

/*
    public MutableLiveData<Bitmap> getLiveForecastMapGluide(URL url) {
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