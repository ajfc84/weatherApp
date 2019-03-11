package com.passageweather;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.passageweather.utils.Constants;

public class OceanAdapter extends RecyclerView.Adapter<OceanAdapter.OceanViewHolder> {
    private int [][] mMapIds;
    private String [][] mLabels;
    private int mLevel;
    private Context mContext;

    static class OceanViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mText;

        OceanViewHolder(View view) {
            super(view);
            mImage = view.findViewById(R.id.iv_option);
            mText = view.findViewById(R.id.tv_option);
        }

    }

    OceanAdapter(int [][] mapIds, String [][] labels, Context context) {
        mMapIds = mapIds;
        mLabels = labels;
        mContext = context;
        mLevel = 0;
    }

    @NonNull
    @Override
    public OceanAdapter.OceanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_list_item, viewGroup, false);

        return new OceanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OceanAdapter.OceanViewHolder holder, int position) {
        if(mLevel > 0) {
            showMap("");
            return;
        }
        holder.mImage.setImageResource(mMapIds[mLevel][position]);
        holder.mText.setText(mLabels[mLevel][position]);
        holder.itemView.setId(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raiseLevel();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mLabels == null || mMapIds == null) return 0;
        return mLabels.length;
    }

    /*
    *   Raise the level of the Menu
    *   Level 0 = root menu
    *   Level 1 = sub menu
    *
     */
    private void raiseLevel() {
        mLevel = 1;
    }

    /*
    * Call the map Activity
    *
    * @param name - map path name to show
     */

    void showMap(String name) {
        Intent intent = new Intent(mContext, MapActivity.class);
        intent.putExtra(Constants.INTENT_MAP_KEY, name);
        mContext.startActivity(intent);
    }

}
