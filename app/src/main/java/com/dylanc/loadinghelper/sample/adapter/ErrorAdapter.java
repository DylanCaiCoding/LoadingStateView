package com.dylanc.loadinghelper.sample.adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    return new ViewHolder(inflater.inflate(R.layout.layout_error, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder) {
    holder.btnReload.setOnClickListener(v -> {
      if (holder.getOnReloadListener() != null) {
        holder.getOnReloadListener().onReload();
      }
    });
  }

  public static class ViewHolder extends LoadingHelper.ViewHolder {

    private final View btnReload;

    ViewHolder(@NonNull View rootView) {
      super(rootView);
      btnReload = rootView.findViewById(R.id.btn_reload);
    }
  }
}
