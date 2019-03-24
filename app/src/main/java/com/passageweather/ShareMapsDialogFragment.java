package com.passageweather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.passageweather.model.MapViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
            if(fileNames[i].startsWith("maps" + region + variable))
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

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

}
