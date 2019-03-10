package com.passageweather;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Resources res = getResources();
        TypedArray maps = res.obtainTypedArray(R.array.options_root_maps);
        int [] mapsIds = new int[maps.length()];
        for(int i = 0; i < maps.length(); i++)
            mapsIds[i] = maps.getResourceId(i, 0);
        maps.recycle(); /* Clean the TypedArray */
        mAdapter = new OceanAdapter(mapsIds, res.getStringArray(R.array.options_root_labels));
        recyclerView.setAdapter(mAdapter);
    }
}
