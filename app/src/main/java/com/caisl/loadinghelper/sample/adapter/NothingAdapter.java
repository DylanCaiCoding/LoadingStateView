package com.caisl.loadinghelper.sample.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.caisl.loadinghelper.LoadingHelper;

/**
 * @author caisl
 * @since 2019/6/27
 */
public class NothingAdapter extends LoadingHelper.Adapter {
  public static final int VIEW_TYPE_NOTHING = 2;

  @NonNull
  @Override
  public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingHelper.ViewHolder(new View(parent.getContext()));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {

  }
}
