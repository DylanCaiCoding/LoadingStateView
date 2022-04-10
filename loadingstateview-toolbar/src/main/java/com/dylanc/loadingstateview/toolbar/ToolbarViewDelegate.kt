@file:Suppress("unused")

package com.dylanc.loadingstateview.toolbar

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType

typealias ToolbarFactory = () -> ToolbarViewDelegate

var toolbarFactory: ToolbarFactory = { SimpleToolbarViewDelegate() }

context(LoadingState)
fun Activity.setToolbar(titleId: Int, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null) =
  setToolbar(getString(titleId), navBtnType, block)

context(LoadingState)
fun Activity.setToolbar(title: String? = null, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null) =
  setHeaders(ToolbarViewDelegate(title, navBtnType, block))

context(LoadingState)
fun Fragment.setToolbar(titleId: Int, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null) =
  setToolbar(getString(titleId), navBtnType, block)

context(LoadingState)
fun Fragment.setToolbar(title: String? = null, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null) =
  setHeaders(ToolbarViewDelegate(title, navBtnType, block))

context(LoadingState)
fun Activity.updateToolbar(block: ToolbarConfig.() -> Unit) =
  updateView<ToolbarViewDelegate.ViewHolder>(ViewType.TITLE) { update(block) }

context(LoadingState)
fun Fragment.updateToolbar(block: ToolbarConfig.() -> Unit) =
  updateView<ToolbarViewDelegate.ViewHolder>(ViewType.TITLE) { update(block) }

fun ToolbarViewDelegate(title: String? = null, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null) =
  toolbarFactory().apply { config = ToolbarConfig(title, navBtnType).apply { block?.invoke(this) } }

abstract class ToolbarViewDelegate : LoadingStateView.ViewDelegate<ToolbarViewDelegate.ViewHolder>(ViewType.TITLE) {
  internal lateinit var config: ToolbarConfig

  override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup) =
    ViewHolder(onCreateView(inflater, parent).apply { onBindView(this, config) })

  abstract fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View

  abstract fun onBindView(view: View, config: ToolbarConfig)

  inner class ViewHolder(view: View) : LoadingStateView.ViewHolder(view) {
    fun update(block: ToolbarConfig.() -> Unit) = onBindView(rootView, config.apply(block))
  }
}
