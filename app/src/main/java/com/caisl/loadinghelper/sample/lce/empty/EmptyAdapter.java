package com.caisl.loadinghelper.sample.lce.empty;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;

/**
 * @author caisl
 * @since 2019/5/18
 */
public class EmptyAdapter extends LoadingHelper.Adapter<LoadingHelper.ViewHolder> {

  private TextView mTvEmptyText;

  @NonNull
  @Override
  public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    final View view = inflater.inflate(R.layout.lce_layout_empty_view, parent, false);
    mTvEmptyText = view.findViewById(R.id.tv_empty_text);
    return new LoadingHelper.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {
//    if (config == null){
//      return;
//    }
//    setText(mTvEmptyText, config.getEmptyText());
  }
}
