package com.caisl.loadinghelper.sample.lce.title;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;

/**
 * @author caisl
 * @since 2019/5/18
 */
public class TitleAdapter extends LoadingHelper.Adapter<TitleAdapter.TitleViewHolder> {

  private TitleConfig.Type mType;

  public TitleAdapter() {
  }

  public TitleAdapter(TitleConfig.Type type) {
    mType = type;
  }

  @NonNull
  @Override
  public TitleAdapter.TitleViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new TitleViewHolder(new Toolbar(parent.getContext()));
  }

  @Override
  public void onBindViewHolder(@NonNull final TitleAdapter.TitleViewHolder holder) {
    if (mType != null && mType == TitleConfig.Type.BACK) {
      holder.toolbar.setNavigationIcon(R.drawable.back_icon);
      holder.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ((Activity) holder.rootView.getContext()).finish();
        }
      });
    }
  }

  public class TitleViewHolder extends LoadingHelper.ViewHolder {

    private Toolbar toolbar;

    public TitleViewHolder(@NonNull View rootView) {
      super(rootView);
      final Context context = rootView.getContext();
      toolbar = (Toolbar) rootView;
      toolbar.setTitle("LoadingHelper");
      toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
      toolbar.setTitleTextColor(ContextCompat.getColor(context, android.R.color.white));
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        toolbar.setElevation(dp2px(context, 2));
      }
    }

    private int dp2px(Context context, float dpValue) {
      return (int) Math.ceil(dpValue * context.getResources().getDisplayMetrics().density);
    }
  }
}
