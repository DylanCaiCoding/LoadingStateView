package com.caisl.loadinghelper.sample.lce.loading;

import android.support.annotation.NonNull;
import android.text.TextUtils;
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
public class LoadingAdapter extends LoadingHelper.Adapter< LoadingAdapter.LoadingViewHolder> {

  @NonNull
  @Override
  public LoadingViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingViewHolder(inflater.inflate(R.layout.lce_layout_loading_view, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingViewHolder holder) {
//    if (config != null) {
//      holder.setLoadingText(config.getLoadingText());
//    }
  }

  public class LoadingViewHolder extends LoadingHelper.ViewHolder{
    private TextView mtvLoadingText;

    public LoadingViewHolder(@NonNull View rootView) {
      super(rootView);
      mtvLoadingText = rootView.findViewById(R.id.tv_loading_text);
    }

    public void setLoadingText(String loadingText){
      if (TextUtils.isEmpty(loadingText)) {
        mtvLoadingText.setVisibility(View.GONE);
      } else {
        mtvLoadingText.setText(loadingText);
        mtvLoadingText.setVisibility(View.VISIBLE);
      }
    }
  }
}
