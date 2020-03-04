package com.dylanc.loadinghelper.sample.adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dylanc.loadinghelper.LoadingHelper;

/**
 * @author Dylan Cai
 * @since 2019/6/27
 */
public class NothingAdapter extends LoadingHelper.Adapter {

  @NonNull
  @Override
  public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingHelper.ViewHolder(new View(parent.getContext()));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {

  }
}
