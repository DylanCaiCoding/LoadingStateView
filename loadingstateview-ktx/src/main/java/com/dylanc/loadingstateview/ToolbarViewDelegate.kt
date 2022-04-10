@file:Suppress("unused")

package com.dylanc.loadingstateview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

typealias ToolbarFactory = () -> ToolbarViewDelegate

lateinit var toolbarFactory: ToolbarFactory

fun ToolbarViewDelegate(title: String? = null, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null) =
  toolbarFactory().apply { config = ToolbarConfig(title, navBtnType).apply { block?.invoke(this) } }

abstract class ToolbarViewDelegate : LoadingStateView.ViewDelegate(ViewType.TITLE) {
  internal lateinit var config: ToolbarConfig

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup) =
    onCreateToolbar(inflater, parent).apply { bind(config) }

  abstract fun onCreateToolbar(inflater: LayoutInflater, parent: ViewGroup): View

  abstract fun bind(config: ToolbarConfig)
}
