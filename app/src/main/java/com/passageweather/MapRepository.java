package com.passageweather;

import android.graphics.Bitmap;

import com.passageweather.utils.ImageClient;
import com.passageweather.utils.MyApp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MapRepository {

    MutableLiveData<Bitmap> getMap(URL url) {
        return null;
    }

/*
    MutableLiveData<List<Bitmap>> getMaps(List<URL> urls) {
        ImageClient client = ImageClient.newInstance(MyApp.getAppContext());
        MutableLiveData<List<Bitmap>> maps = new MutableLiveData<>();
        List<Bitmap> list = new ArrayList<>();
        for(URL u : urls) {
            list.add(client.makeRequest(u));
        }
        return maps.setValue(list);
    }
*/

}
