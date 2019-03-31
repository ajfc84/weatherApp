package com.passageweather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.passageweather.config.MyApp;
import com.passageweather.modelview.MapViewModel;
import com.passageweather.utils.Utils;

import java.io.File;
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
        String [] fileLabels = model.getForecastFileLabels();
        List<Integer> selectedItems = new ArrayList<>();
        builder.setTitle(R.string.share_maps_dialog_title)
                .setMultiChoiceItems(fileLabels, null, new DialogInterface.OnMultiChoiceClickListener() {
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
                        File [] files = model.getForecastMaps();
                        for(Integer i : selectedItems) {
                            mapsToShare.add(FileProvider.getUriForFile(ctx, "com.passageweather.fileprovider", files[i]));
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
