package com.dylanc.loadinghelper.sample.adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;

/**
 * @author Dylan Cai
 * @since 2019/5/18
 */
public class LoadingAdapter extends LoadingHelper.Adapter<LoadingHelper.ViewHolder> {

  private int height = ViewGroup.LayoutParams.MATCH_PARENT;

  public LoadingAdapter() {
  }

  public LoadingAdapter(int height) {
    this.height = height;
  }

  @NonNull
  @Override
  public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingHelper.ViewHolder(inflater.inflate(R.layout.layout_loading, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {
    ViewGroup.LayoutParams layoutParams = holder.getRootView().getLayoutParams();
    layoutParams.height = height;
    holder.getRootView().setLayoutParams(layoutParams);
  }
}
