package com.dylanc.loadinghelper.sample.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import com.dylanc.loadinghelper.LoadingHelper;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 */
public class FadeAnimation implements LoadingHelper.Animation {

  @Override
  public void onStartShowAnimation(@NotNull View view, @NotNull Object viewType) {
    view.setAlpha(0);
    view.animate().alpha(1).setDuration(500).setListener(null);
  }

  @Override
  public void onStartHideAnimation(@NotNull View view, @NotNull Object viewType) {
    view.animate().alpha(0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        view.setAlpha(1);
        view.setVisibility(View.GONE);
      }
    });
  }
}
