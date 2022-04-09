package com.dylanc.loadingstateview.toolbar

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingState.Companion.setHeaders
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType

typealias ToolbarStyle = () -> ToolbarViewDelegate

var defaultToolbarStyle: ToolbarStyle = { DefaultToolbarViewDelegate() }

context(LoadingState)
fun Activity.setToolbar(titleId: Int, navIconType: NavIconType = NavIconType.ICON, block: (ToolbarConfig.() -> Unit)? = null) =
  setToolbar(getString(titleId), navIconType, block)

context(LoadingState)
fun Activity.setToolbar(title: String? = null, navIconType: NavIconType = NavIconType.ICON, block: (ToolbarConfig.() -> Unit)? = null) =
  setHeaders(
    ViewType.TITLE to defaultToolbarStyle().also { it.config = ToolbarConfig(this, title, navIconType).apply { block?.invoke(this) } }
  )

context(LoadingState)
fun Fragment.setToolbar(titleId: Int, navIconType: NavIconType = NavIconType.ICON, block: (ToolbarConfig.() -> Unit)? = null) =
  setToolbar(getString(titleId), navIconType, block)

context(LoadingState)
fun Fragment.setToolbar(title: String? = null, navIconType: NavIconType = NavIconType.ICON, block: (ToolbarConfig.() -> Unit)? = null) =
  setHeaders(
    ViewType.TITLE to defaultToolbarStyle().also { it.config = ToolbarConfig(requireContext(), title, navIconType).apply { block?.invoke(this) } }
  )

abstract class ToolbarViewDelegate : LoadingStateView.ViewDelegate<ToolbarViewDelegate.ViewHolder>() {
  internal lateinit var config: ToolbarConfig

  override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup) =
    ViewHolder(onCreateView(inflater, parent)).apply { bind(config) }

  abstract fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View

  abstract fun onBindView(view: View, config: ToolbarConfig)

  inner class ViewHolder(view: View) : LoadingStateView.ViewHolder(view) {
    fun bind(config: ToolbarConfig) = onBindView(rootView, config)
  }
}
