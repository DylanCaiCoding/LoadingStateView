/*
 * Copyright (c) 2019. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dylanc.loadingstateview

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

class LoadingStateDelegate : LoadingState {
  private var loadingStateView: LoadingStateView? = null

  override fun Activity.decorateContentView(decorative: Decorative) {
    findViewById<ViewGroup>(android.R.id.content).getChildAt(0).decorate(decorative)
  }

  override fun View.decorate(decorative: Decorative): View =
    when {
      !decorative.isDecorated -> this
      decorative.contentView == null ->
        LoadingStateView(this, decorative).also { loadingStateView = it }.decorView
      else -> {
        loadingStateView = LoadingStateView(decorative.contentView!!, decorative)
        this
      }
    }

  override fun registerView(vararg viewDelegates: LoadingStateView.ViewDelegate) {
    loadingStateView?.register(*viewDelegates)
  }

  override fun Activity.setToolbar(@StringRes titleId: Int, navBtnType: NavBtnType, block: (ToolbarConfig.() -> Unit)?) {
    setToolbar(getString(titleId), navBtnType, block)
  }

  override fun Activity.setToolbar(title: String?, navBtnType: NavBtnType, block: (ToolbarConfig.() -> Unit)?) {
    loadingStateView?.setHeaders(ToolbarViewDelegate(title, navBtnType, block))
  }

  override fun Fragment.setToolbar(@StringRes titleId: Int, navBtnType: NavBtnType, block: (ToolbarConfig.() -> Unit)?) {
    setToolbar(getString(titleId), navBtnType, block)
  }

  override fun Fragment.setToolbar(title: String?, navBtnType: NavBtnType, block: (ToolbarConfig.() -> Unit)?) {
    loadingStateView?.addChildHeaders(ToolbarViewDelegate(title, navBtnType, block))
  }

  override fun Activity.setHeaders(vararg delegates: LoadingStateView.ViewDelegate) {
    loadingStateView?.setHeaders(*delegates)
  }

  override fun Fragment.setHeaders(vararg delegates: LoadingStateView.ViewDelegate) {
    loadingStateView?.addChildHeaders(*delegates)
  }

  override fun Activity.setDecorView(delegate: LoadingStateView.DecorViewDelegate) {
    loadingStateView?.setDecorView(delegate)
  }

  override fun Fragment.setDecorView(delegate: LoadingStateView.DecorViewDelegate) {
    loadingStateView?.addChildDecorView(delegate)
  }

  override fun showLoadingView(animation: LoadingStateView.Animation?) {
    loadingStateView?.showLoadingView(animation)
  }

  override fun showContentView(animation: LoadingStateView.Animation?) {
    loadingStateView?.showContentView(animation)
  }

  override fun showErrorView(animation: LoadingStateView.Animation?) {
    loadingStateView?.showErrorView(animation)
  }

  override fun showEmptyView(animation: LoadingStateView.Animation?) {
    loadingStateView?.showEmptyView(animation)
  }

  override fun showCustomView(viewType: Any) {
    loadingStateView?.showView(viewType)
  }

  override fun updateToolbar(block: ToolbarConfig.() -> Unit) {
    updateView<BaseToolbarViewDelegate>(ViewType.TITLE) { onBindToolbar(config.apply(block)) }
  }

  override fun <T : LoadingStateView.ViewDelegate> updateView(viewType: Any, block: T.() -> Unit) {
    loadingStateView?.updateViewDelegate(viewType, block)
  }

  override fun ToolbarViewDelegate(title: String?, navBtnType: NavBtnType, block: (ToolbarConfig.() -> Unit)?) =
    requireNotNull(loadingStateView?.getViewDelegate<BaseToolbarViewDelegate>(ViewType.TITLE)) {
      "ToolbarViewDelegate must be registered before."
    }.apply {
      config = ToolbarConfig(title, navBtnType).apply { block?.invoke(this) }
    }
}