package com.passageweather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class OceanAdapter extends RecyclerView.Adapter<OceanAdapter.OceanViewHolder> {
    private int [][] mMapIds;
    private String [][] mLabels;
    private int mLevel;

    static class OceanViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mText;

        OceanViewHolder(View view) {
            super(view);
            mImage = view.findViewById(R.id.iv_option);
            mText = view.findViewById(R.id.tv_option);
        }

    }

    OceanAdapter(int [][] mapIds, String [][] labels) {
        mMapIds = mapIds;
        mLabels = labels;
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
        holder.mImage.setImageResource(mMapIds[mLevel][position]);
        holder.mText.setText(mLabels[mLevel][position]);
        holder.itemView.setId(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLevel(v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mLabels == null || mMapIds == null) return 0;
        return mLabels.length;
    }

    private void setLevel(int level) {
        mLevel = level;
        notifyDataSetChanged();
    }

}
