package com.passageweather.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.passageweather.modelview.MapViewModel;
import com.passageweather.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class ImageClient {
    private static ImageClient instance;
    private static RequestQueue queue;
    private static Context context;

    private ImageClient(Context context) {
        queue = Volley.newRequestQueue(context);
        ImageClient.context = context;
    }

    public synchronized static ImageClient newInstance(Context context) {
        if(instance == null) instance = new ImageClient(context);
        return instance;
    }

    public static Bitmap makeRequest(URL url) {
        List<Bitmap> data = new ArrayList<>();
        ImageRequest request = new ImageRequest(
                url.toString(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap image) {
                        data.add(image);
/*
                        try {
                            Map file = new Map(context.getFilesDir(), "pw_" + ".png");
                            OutputStream outputStream = new FileOutputStream(file);
                            image.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
*/
                    }
                },
                800,
                600,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(this.getClass().getName(), error.getMessage());
                    }
                }
        );
        queue.add(request);
        return data.get(0);
    }

    public static void makeUIRequest(Fragment fragment) {
        FragmentActivity activity = fragment.getActivity();
        MapViewModel model = ViewModelProviders.of(activity).get(MapViewModel.class);
        ImageView imageView = activity.findViewById(R.id.iv_map);
        ImageRequest request = new ImageRequest(
                NetUtils.buildMapURL(model, fragment.getArguments().getInt(Constants.INTENT_FORECAST_KEY)).toString(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap image) {
                        imageView.setImageBitmap(image);
                    }
                },
                800,
                600,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(this.getClass().getName(), error.getMessage());
                    }
                }
        );
        queue.add(request);
    }

}
