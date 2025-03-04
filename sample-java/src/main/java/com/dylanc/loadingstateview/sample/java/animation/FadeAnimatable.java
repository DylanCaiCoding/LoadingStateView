package com.dylanc.loadingstateview.sample.java.animation;

import android.view.View;

import androidx.annotation.NonNull;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.ViewType;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 */
public class FadeAnimatable implements LoadingStateView.Animatable {

  private static final long DEFAULT_DURATION = 500;
  private final long duration;

  public FadeAnimatable() {
    this(DEFAULT_DURATION);
  }

  public FadeAnimatable(long duration) {
    this.duration = duration;
  }

  @Override
  public void toggleViewsAnimation(@NonNull View showView, @NonNull View hideView, @NonNull Object showViewType, @NonNull Object hideViewType) {
    showView.setAlpha(0);
    showView.setVisibility(View.VISIBLE);
    showView.animate().alpha(1).setDuration(duration);

    if (showViewType == ViewType.LOADING && hideViewType == ViewType.CONTENT) {
      hideView.setVisibility(View.GONE);
    } else {
      hideView.animate().alpha(0).setDuration(duration).withEndAction(() -> {
        hideView.setAlpha(1);
        hideView.setVisibility(View.GONE);
      });
    }
  }
}
