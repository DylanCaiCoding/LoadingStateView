package com.dylanc.loadinghelper.sample.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dylanc.loadinghelper.LoadingHelper;

/**
 * @author Dylan Cai
 * @since 2019/6/27
 */
public class SimpleContentAdapter extends LoadingHelper.ContentAdapter<SimpleContentAdapter.ViewHolder> {
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull View contentView) {
    return new ViewHolder(contentView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder) {

  }

  class ViewHolder extends LoadingHelper.ViewHolder {

    ViewHolder(@NonNull View rootView) {
      super(rootView);
    }
  }
}
