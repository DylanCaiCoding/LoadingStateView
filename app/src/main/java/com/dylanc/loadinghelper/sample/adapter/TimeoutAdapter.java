package com.dylanc.loadinghelper.sample.adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;

/**
 * @author Dylan Cai
 * @since 2019/6/25
 */
public class TimeoutAdapter extends LoadingHelper.Adapter<LoadingHelper.ViewHolder> {

  @NonNull
  @Override
  public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingHelper.ViewHolder(inflater.inflate(R.layout.layout_timeout, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {
    holder.getRootView().setOnClickListener(v -> {
      if (holder.getOnReloadListener() != null) {
        holder.getOnReloadListener().onReload();
      }
    });
  }
}
