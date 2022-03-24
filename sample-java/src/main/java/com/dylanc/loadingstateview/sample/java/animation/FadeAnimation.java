package com.dylanc.loadingstateview.sample.java.animation;

import android.view.View;

import com.dylanc.loadingstateview.LoadingStateView;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 */
public class FadeAnimation implements LoadingStateView.Animation {

  private static final long DEFAULT_DURATION = 500;
  private final long duration;

  public FadeAnimation() {
    this(DEFAULT_DURATION);
  }

  public FadeAnimation(long duration) {
    this.duration = duration;
  }

  @Override
  public void onStartShowAnimation(@NotNull View view, @NotNull Object viewType) {
    view.setAlpha(0);
    view.animate().alpha(1).setDuration(duration);
  }

  @Override
  public void onStartHideAnimation(@NotNull View view, @NotNull Object viewType) {
    view.setAlpha(1);
    view.animate().alpha(0).setDuration(duration).withEndAction(() -> {
      view.setAlpha(1);
      view.setVisibility(View.GONE);
    });
  }
}
