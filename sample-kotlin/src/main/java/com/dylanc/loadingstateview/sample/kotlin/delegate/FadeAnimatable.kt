package com.dylanc.loadingstateview.sample.kotlin.delegate

import android.view.View
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType

/**
 * @author Dylan Cai
 */
class FadeAnimatable(private val duration: Long = 600) : LoadingStateView.Animatable {
  override fun toggleViewsAnimation(showView: View, hideView: View, showViewType: Any, hideViewType: Any) {
    if (showViewType === ViewType.LOADING && hideViewType === ViewType.CONTENT) {
      showView.visibility = View.VISIBLE
      hideView.visibility = View.GONE
    } else {
      showView.alpha = 0f
      showView.visibility = View.VISIBLE
      showView.animate().alpha(1f).setDuration(duration)
      hideView.animate().alpha(0f).setDuration(duration).withEndAction {
        hideView.alpha = 1f
        hideView.visibility = View.GONE
      }
    }
  }
}
