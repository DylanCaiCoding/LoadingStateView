package com.dylanc.loadinghelper.sample.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;

/**
 * @author Dylan Cai
 * @since 2019/6/25
 */
public class TimeoutAdapter extends LoadingHelper.Adapter<TimeoutAdapter.ViewHolder> {

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new ViewHolder(inflater.inflate(R.layout.loading_layout_timeout, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder) {

  }

  static class ViewHolder extends LoadingHelper.ViewHolder {

    ViewHolder(@NonNull View rootView) {
      super(rootView);
      rootView.setOnClickListener(v -> {
        if (getOnReloadListener() != null) {
          getOnReloadListener().onReload();
        }
      });
    }
  }
}
