package com.dylanc.loadinghelper.sample.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;

/**
 * @author Dylan Cai
 * @since 2019/5/18
 */
public class ErrorAdapter extends LoadingHelper.Adapter<ErrorAdapter.ViewHolder> {

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new ViewHolder(inflater.inflate(R.layout.loading_layout_error, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder) {

  }

  public static class ViewHolder extends LoadingHelper.ViewHolder {

    ViewHolder(@NonNull View rootView) {
      super(rootView);
      rootView.findViewById(R.id.btn_reload).setOnClickListener(v -> {
        if (getOnReloadListener() != null) {
          getOnReloadListener().onReload();
        }
      });
    }
  }
}
