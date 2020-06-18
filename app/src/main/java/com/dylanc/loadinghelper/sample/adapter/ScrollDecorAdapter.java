package com.dylanc.loadinghelper.sample.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 * @since 2020/2/27
 */
public class ScrollDecorAdapter extends LoadingHelper.DecorAdapter {
  private String title;

  public ScrollDecorAdapter(String title) {
    this.title = title;
  }

  @NotNull
  @Override
  @SuppressLint("InflateParams")
  public View onCreateDecorView(@NotNull LayoutInflater inflater) {
    View view = inflater.inflate(R.layout.layout_scrolling_toolbar, null);
    Activity activity = (Activity) inflater.getContext();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    Toolbar toolbar = view.findViewById(R.id.toolbar);
    toolbar.setTitle(title);
    toolbar.setNavigationOnClickListener(v -> activity.finish());
    return view;
  }

  @NotNull
  @Override
  public ViewGroup getContentParent(@NotNull View decorView) {
    return decorView.findViewById(R.id.loading_container);
  }

}
