package com.dylanc.loadinghelper.sample.adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;

/**
 * @author Dylan Cai
 * @since 2019/6/24
 */
public class WaterLoadingAdapter extends LoadingHelper.Adapter {

  @NonNull
  @Override
  public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingHelper.ViewHolder(inflater.inflate(R.layout.layout_water_loading, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {

  }
}
