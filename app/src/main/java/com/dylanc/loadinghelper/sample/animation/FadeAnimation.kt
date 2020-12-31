package com.dylanc.loadinghelper.sample.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.dylanc.loadinghelper.LoadingHelper

class FadeAnimation : LoadingHelper.Animation {
  override fun onStartShowAnimation(view: View, viewType: Any) {
    view.alpha = 0f
    view.animate().alpha(1f).duration = 500
  }

  override fun onStartHideAnimation(view: View, viewType: Any) {
    view.animate().alpha(0f).setDuration(500).setListener(object :
      AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator?) {
        view.visibility = View.GONE
      }
    })
  }
}