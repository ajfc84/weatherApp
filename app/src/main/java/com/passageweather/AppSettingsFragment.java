package com.passageweather;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passageweather.config.MyApp;
import com.passageweather.utils.Utils;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

public class AppSettingsFragment extends PreferenceFragmentCompat {
    private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(getString(R.string.sp_auto_download_key))) {
                PreferenceCategory pc = null;
                pc = findPreference(getString(R.string.pc_variable_key));
                if(sharedPreferences.getBoolean(getString(R.string.sp_auto_download_key), getResources().getBoolean(R.bool.auto_download_default))) {
                    Utils.createForecastAlarm();
                    pc.setVisible(true);
                }
                else {
                    Utils.removeForecastAlarm();
                    pc.setVisible(false);
                }
            }
        }
    };

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.app_settings, rootKey);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
        PreferenceCategory pc = null;
        pc = findPreference(getString(R.string.pc_variable_key));
        if(preferences.getBoolean(getString(R.string.sp_auto_download_key), getResources().getBoolean(R.bool.auto_download_default))) {
            pc.setVisible(true);
        }
        else
            pc.setVisible(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        v.setBackgroundColor(getResources().getColor(R.color.colorSecondary)); // Correcting transparent background
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // TODO (15) Only works with MainActivity needs to also work with NavMenuFragment
        getActivity().setTitle(R.string.app_name);
    }

}
