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
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

interface LoadingState : Decorative, OnReloadListener {
  val loadingStateViewType: Any?

  @Deprecated("Use Activity.decorateContentView(this) instead", ReplaceWith("decorateContentView(decorative)"))
  fun Activity.decorateContentView(listener: OnReloadListener, decorative: Decorative) = decorateContentView(decorative)

  fun Activity.decorateContentView(decorative: Decorative)

  @Deprecated("Use View.decorate(this) instead", ReplaceWith("decorate(decorative)"))
  fun View.decorate(listener: OnReloadListener, decorative: Decorative): View = decorate(decorative)

  fun View.decorate(decorative: Decorative): View

  fun registerView(vararg viewDelegates: LoadingStateView.ViewDelegate)

  fun Activity.setToolbar(@StringRes titleId: Int, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null)

  fun Activity.setToolbar(title: String? = null, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null)

  fun Fragment.setToolbar(@StringRes titleId: Int, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null)

  fun Fragment.setToolbar(title: String? = null, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null)

  fun Activity.setHeaders(vararg delegates: LoadingStateView.ViewDelegate)

  fun Fragment.setHeaders(vararg delegates: LoadingStateView.ViewDelegate)

  fun Activity.setDecorView(delegate: LoadingStateView.DecorViewDelegate)

  fun Fragment.setDecorView(delegate: LoadingStateView.DecorViewDelegate)

  fun showLoadingView(animatable: LoadingStateView.Animatable? = LoadingStateView.defaultAnimatable)

  fun showContentView(animatable: LoadingStateView.Animatable? = LoadingStateView.defaultAnimatable)

  fun showErrorView(animatable: LoadingStateView.Animatable? = LoadingStateView.defaultAnimatable)

  fun showEmptyView(animatable: LoadingStateView.Animatable? = LoadingStateView.defaultAnimatable)

  fun showCustomView(viewType: Any, animatable: LoadingStateView.Animatable? = LoadingStateView.defaultAnimatable)

  fun updateToolbar(block: ToolbarConfig.() -> Unit)

  fun <T : LoadingStateView.ViewDelegate> updateView(viewType: Any, block: T.() -> Unit)

  @Suppress("FunctionName")
  fun ToolbarViewDelegate(
    title: String? = null, navBtnType: NavBtnType = NavBtnType.ICON, block: (ToolbarConfig.() -> Unit)? = null
  ): BaseToolbarViewDelegate
}