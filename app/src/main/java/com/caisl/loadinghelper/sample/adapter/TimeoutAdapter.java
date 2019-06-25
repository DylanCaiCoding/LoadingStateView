package com.caisl.loadinghelper.sample.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;

/**
 * @author caisl
 * @since 2019/6/25
 */
public class TimeoutAdapter extends LoadingHelper.Adapter<TimeoutAdapter.TimeoutViewHolder> {
  public static final int VIEW_TYPE_TIMEOUT = 1;

  @NonNull
  @Override
  public TimeoutViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new TimeoutViewHolder(inflater.inflate(R.layout.loading_layout_timeout, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull TimeoutViewHolder holder) {

  }

  class TimeoutViewHolder extends LoadingHelper.ViewHolder {

    TimeoutViewHolder(@NonNull View rootView) {
      super(rootView);
      rootView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (retryTask != null) {
            retryTask.run();
          }
        }
      });
    }
  }
}
