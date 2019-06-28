package com.caisl.loadinghelper.sample.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.caisl.loadinghelper.LoadingHelper;

/**
 * @author caisl
 * @since 2019/6/27
 */
public class SimpleContentAdapter extends LoadingHelper.ContentAdapter<LoadingHelper.ContentViewHolder> {
  @Override
  public LoadingHelper.ContentViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent,
                                                            @NonNull View contentView) {
    return new LoadingHelper.ContentViewHolder(contentView);
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ContentViewHolder holder) {
    if (holder.activity != null) {
      holder.activity.getWindow().setFlags(
          WindowManager.LayoutParams.FLAG_FULLSCREEN,
          WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
  }
}
