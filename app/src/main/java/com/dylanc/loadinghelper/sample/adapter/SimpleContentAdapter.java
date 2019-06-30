package com.dylanc.loadinghelper.sample.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.dylanc.loadinghelper.LoadingHelper;

/**
 * @author Dylan Cai
 * @since 2019/6/27
 */
public class SimpleContentAdapter extends LoadingHelper.ContentAdapter<SimpleContentAdapter.ViewHolder> {
  @Override
  public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent,
                                       @NonNull View contentView) {
    return new ViewHolder(contentView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder) {

  }

  class ViewHolder extends LoadingHelper.ContentViewHolder {

    ViewHolder(@NonNull View rootView) {
      super(rootView);
    }

    @Override
    public void onCreate(@Nullable Activity activity) {
      super.onCreate(activity);
      if (activity != null) {
        activity.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
      }
    }
  }
}
