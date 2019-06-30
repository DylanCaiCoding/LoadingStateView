package com.dylanc.loadinghelper.sample.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;

/**
 * @author Dylan Cai
 * @since 2019/5/18
 */
public class LoadingAdapter extends LoadingHelper.Adapter<LoadingHelper.ViewHolder> {

  @NonNull
  @Override
  public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingHelper.ViewHolder(inflater.inflate(R.layout.loading_layout_loading, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {
  }
}
