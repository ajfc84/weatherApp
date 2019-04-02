package com.passageweather.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;

import com.passageweather.R;
import com.passageweather.config.MyApp;
import com.passageweather.model.Map;
import com.passageweather.modelview.MapRepository;
import com.passageweather.utils.Constants;
import com.passageweather.utils.NetUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import androidx.core.content.FileProvider;
import androidx.preference.PreferenceManager;

public class ForecastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action == null) action = "";
        if (action.equals("android.intent.action.ACTION_NOTIFICATION_CLICKED")) {

        }
        else  if(action.equals("android.intent.action.DOWNLOAD_COMPLETE")) {
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
            Uri uri = manager.getUriForDownloadedFile(downloadId);
            if(uri == null) return; // if download unsuccessful
            // TODO (20) uri must be the url from the site with region and variable values
            String values [] = uri.getLastPathSegment().split("/");
            String region = values[1];
            String variable = values[2];
            ZipFile zFile = null;
            try {
                zFile = new ZipFile(uri.getPath());
                Enumeration<? extends ZipEntry> entries = zFile.entries();
                while(entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    String fileName = entry.getName();
                    InputStream in = zFile.getInputStream(entry);
                    FileOutputStream out = new FileOutputStream(
                            context.getFilesDir() +
                                    NetUtils.buildMapsRelativePath(region, variable) +
                                    fileName);
                    int data = in.read();
                    while (data != -1) {
                        out.write(data);
                        data = in.read();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(action.equals(Constants.INTENT_AUTO_MODE)) {
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = null;
            String region = Constants.REGION_MEDITERRANEAN_SEA;
            String variable = Constants.VAR_WIND_GFS;
            for(String A : Constants.FORECAST_ARCHIVES) {
                request = new DownloadManager.Request(NetUtils.buildArchiveUri(region, variable, A));
                request.setDestinationInExternalFilesDir(context, null, "maps/" + region + "/" + variable + "/");
                request.setTitle("passagerweather.com forecast");
                request.setDescription("Downloading " + region + "->" + variable + " archives...");
/*
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setVisibleInDownloadsUi(true);
*/

                manager.enqueue(request);
            }
        }
        else if (action.equals(Constants.INTENT_LAZY_MODE)) {
            Context ctx = MyApp.getAppContext();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
            if(preferences.getBoolean(ctx.getString(R.string.running_first_time_key), ctx.getResources().getBoolean(R.bool.running_first_time))) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(ctx.getString(R.string.running_first_time_key), ctx.getResources().getBoolean(R.bool.not_running_first_time));
                editor.commit();
                // startLazingMode in MapActivity, just set first time to false, next Alarm will updateLazyMaps
            }
            else MapRepository.newInstance().updateLazyMaps();
        }
    }

}
