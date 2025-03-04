package com.dylanc.loadingstateview.sample.kotlin.delegate

import android.view.View
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType

/**
 * @author Dylan Cai
 */
class FadeAnimatable @JvmOverloads constructor(
  private val duration: Long = DEFAULT_DURATION
) : LoadingStateView.Animatable {
  override fun toggleViewsAnimation(showView: View, hideView: View, showViewType: Any, hideViewType: Any) {
    showView.alpha = 0f
    showView.visibility = View.VISIBLE
    showView.animate().alpha(1f).setDuration(duration)

    if (showViewType === ViewType.LOADING && hideViewType === ViewType.CONTENT) {
      hideView.visibility = View.GONE
    } else {
      hideView.animate().alpha(0f).setDuration(duration).withEndAction {
        hideView.alpha = 1f
        hideView.visibility = View.GONE
      }
    }
  }

  companion object {
    private const val DEFAULT_DURATION: Long = 600
  }
}
