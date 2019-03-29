package com.passageweather.modelview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
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
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    private class Wrapper<T> {
        public T obj;
    }

    private Bitmap openVolleyConnection(URL url) {
        final Wrapper<Bitmap> data = new Wrapper();
        RequestQueue queue = MapClient.getInstance(MyApp.getAppContext()).getRequestQueue();
        ImageRequest imageRequest = new ImageRequest(
                url.toString(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        data.obj = response;
                    }
                },
                800,
                600,
                ImageView.ScaleType.CENTER, // <-
                Bitmap.Config.ARGB_8888,
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, "Connection failure!", Toast.LENGTH_LONG).show(); // TODO (99) Give user a warning
                        Log.e(this.getClass().getName(), error.getMessage());
                    }
                });
        MapClient.getInstance(MyApp.getAppContext()).addToRequestQueue(imageRequest);
        return data.obj;
    }

    public MutableLiveData<Bitmap> getLiveForecastMap(URL url) {
        MutableLiveData map = new MutableLiveData();
        mThreadManager.addRunnable(new Runnable() {
            @Override
            public void run() {
                Bitmap image = openVolleyConnection(url);
                map.postValue(image);
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
        return map;
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
                    data.postValue(openVolleyConnection(url));
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
                    map.name = String.format("%03d", forecastNumbers[i]);
                    map.forecastDate = dateFormat.format(date) + " - " + String.format("%02d00", forecastHours[i]) + " UTC";
                    maps[i] = map;
                }
                mapDao.insertMaps(maps);
            }
        });
    }

    public MapLabel [] getForecastMapLabels(String region, String variable) {
        Future data = mThreadManager.addCallable(new Callable() {
            @Override
            public Object call() throws Exception {
                return mapDao.getMapForecastDatesByRegionAndVariable(region, variable);
            }
        });
        MapLabel [] labels = null;
        try {
            labels =(MapLabel[]) data.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return labels;
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
