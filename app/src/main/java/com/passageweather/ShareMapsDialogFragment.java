package com.passageweather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.passageweather.config.MyApp;
import com.passageweather.model.MapViewModel;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class ShareMapsDialogFragment extends DialogFragment {
    private MapViewModel model;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        model = ViewModelProviders.of(getActivity()).get(MapViewModel.class);
        String region = model.getRegion().getValue();
        String variable = model.getVariable().getValue();
        List<String> list = new ArrayList<>();
        String [] fileNames = model.getForecastMapsNames();
        for(int i = 0 ; i < fileNames.length; i++)
            if(fileNames[i].startsWith("maps_" + region + "_" + variable))
                list.add(fileNames[i]);
        String [] items = list.toArray(new String[list.size()]);
        List<Integer> selectedItems = new ArrayList<>();
        builder.setTitle(R.string.share_maps_dialog_title)
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked)
                            selectedItems.add(which);
                        else if (selectedItems.contains(which))
                            selectedItems.remove(Integer.valueOf(which));
                    }
                })
                .setPositiveButton(R.string.share, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        shareIntent.setType("image/png");
                        Context ctx = MyApp.getAppContext();
                        File file = null;
                        ArrayList<Uri> mapsToShare = new ArrayList<>();
                        for(Integer i : selectedItems) {
                            file = new File(ctx.getFilesDir(), items[i]); // TODO (10) Get filename from title
                            mapsToShare.add(FileProvider.getUriForFile(ctx, "com.passageweather.fileprovider", file));
                        }
                        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, mapsToShare);
                        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share_map)));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ;
                    }
                });
        return builder.create();
    }

}
